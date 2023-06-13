package com.cz.spring_boot_netty_demo.http;

import com.cz.spring_boot_netty_demo.http.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoClient {

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().start("127.0.0.1", 8899);
    }

    public void start(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO))
                                    .addLast(new EchoClientHandler());
                        }
                    });
            // 启动客户端
            final ChannelFuture f = client.connect(host, port).sync();
            // 等待连接关闭
            f.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }
    }
}
