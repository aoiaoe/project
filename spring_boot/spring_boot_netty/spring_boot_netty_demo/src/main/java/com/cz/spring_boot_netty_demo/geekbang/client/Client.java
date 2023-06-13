package com.cz.spring_boot_netty_demo.geekbang.client;

import com.cz.spring_boot_netty_demo.geekbang.client.codec.OrderFrameDecode;
import com.cz.spring_boot_netty_demo.geekbang.client.codec.OrderFrameEncoder;
import com.cz.spring_boot_netty_demo.geekbang.client.codec.OrderProtocolDecoder;
import com.cz.spring_boot_netty_demo.geekbang.client.codec.OrderProtocolEncoder;
import com.cz.spring_boot_netty_demo.geekbang.client.handler.ClientIdleCheckHandler;
import com.cz.spring_boot_netty_demo.geekbang.client.handler.KeepAliveHandler;
import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import com.cz.spring_boot_netty_demo.geekbang.common.auth.AuthOperation;
import com.cz.spring_boot_netty_demo.geekbang.common.order.OrderOperation;
import com.cz.spring_boot_netty_demo.geekbang.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class Client {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        // 配置参数, 连接超时时间
        bootstrap.option(NioChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        // pipeline是一个调用链，有来有回
                        // 所以handler必须放最后一个

                        // 写空闲检测处理区
                        .addLast(new ClientIdleCheckHandler())

                        .addLast(new OrderFrameDecode())
                        .addLast(new OrderFrameEncoder())
                        .addLast(new OrderProtocolDecoder())
                        .addLast(new OrderProtocolEncoder())

                        .addLast(new KeepAliveHandler())

                        .addLast(new LoggingHandler(LogLevel.INFO));

            }
        });

        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8090);

        RequestMsg requestMsg = new RequestMsg(IdUtil.nextId(), new OrderOperation(101, "鱼香肉丝"));
        RequestMsg authMsg = new RequestMsg(IdUtil.nextId(), new AuthOperation("admin", "admin"));

        channelFuture.sync();

        // 先发送授权消息
        channelFuture.channel().writeAndFlush(authMsg);
        for (int i = 0; i < 10; i++) {
            channelFuture.channel().writeAndFlush(requestMsg);
        }
        channelFuture.channel().writeAndFlush(requestMsg);
        channelFuture.channel().closeFuture().get();
    }
}
