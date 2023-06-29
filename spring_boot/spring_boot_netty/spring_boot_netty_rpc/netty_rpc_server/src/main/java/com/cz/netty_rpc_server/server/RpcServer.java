package com.cz.netty_rpc_server.server;

import com.cz.netty_rpc_server.serivce.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcServer implements DisposableBean {

    EventLoopGroup boss = null;
    EventLoopGroup worker = null;

    @Autowired
    private RpcServerHandler rpcServerHandler;

    public void startServer(String host, int port){
        try {
            boss = new NioEventLoopGroup(1);
            worker = new NioEventLoopGroup();

            ServerBootstrap server = new ServerBootstrap();

            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast("rpcServerHandler", rpcServerHandler);

                        }
                    });
            ChannelFuture future = server.bind(host, port).sync();
            System.out.println("服务端启动成功");
        }catch (Exception e){
            e.printStackTrace();
            try {
                destroy();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if(boss != null) {
            boss.shutdownGracefully();
        }
        if(worker != null) {
            worker.shutdownGracefully();
        }
    }
}
