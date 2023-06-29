package com.cz.netty_client.client;

import com.cz.netty_client.handler.RpcClintHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.*;

/**
 * 1、连接netty服务店
 * 2、提供关闭连接方法
 * 3、提供消息发送方法
 */
public class RpcClient {

    EventLoopGroup group = null;
    Channel channel = null;
    private String host = "127.0.0.1";
    private int port = 8899;

    private ExecutorService executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        // 初始化客户端
        initClient();
    }

    RpcClintHandler rpcClintHandler = new RpcClintHandler();

    public void initClient() {
        try {
            group = new NioEventLoopGroup();
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_TIMEOUT, 3000)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(rpcClintHandler);
                        }
                    });
             channel = client.connect(host, port).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public void close() {
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    /**
     * 发送消息
     * @param requestMsg
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Object send(String requestMsg) throws ExecutionException, InterruptedException {
        rpcClintHandler.setRequest(requestMsg);
        Future future = executor.submit(rpcClintHandler);
        return future.get();
    }


}
