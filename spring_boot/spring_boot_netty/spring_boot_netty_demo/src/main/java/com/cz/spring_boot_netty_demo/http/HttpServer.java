package com.cz.spring_boot_netty_demo.http;

import com.cz.spring_boot_netty_demo.http.handler.EchoServerHandler;
import com.cz.spring_boot_netty_demo.http.handler.HttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 使用netty实现一个http服务器
 */
public class HttpServer{

    public static void main(String[] args) throws InterruptedException {
        new HttpServer().start(9900);
    }

    public void start(int port) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker);

            b.channel(NioServerSocketChannel.class);

            // 设置处理器
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast("log", new LoggingHandler(LogLevel.INFO))
                            .addLast("codec", new HttpServerCodec())
                            .addLast("excHandler", new HttpServerExpectContinueHandler())
                            .addLast("handler0", new HttpServerHandler());
                }
            });
            // 设置为true则代表启用了TCP SO_KEEPALIVE属性，TCP会主动探测连接状态，即连接保活
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            System.out.println("现在可以使用浏览器访问当前服务器: http://localhost:" + port);
            f.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
