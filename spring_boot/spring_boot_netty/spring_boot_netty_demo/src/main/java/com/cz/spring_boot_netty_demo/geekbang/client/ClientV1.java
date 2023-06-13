package com.cz.spring_boot_netty_demo.geekbang.client;

import com.cz.spring_boot_netty_demo.geekbang.client.codec.*;
import com.cz.spring_boot_netty_demo.geekbang.common.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class ClientV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        // pipeline是一个调用链，有来有回
                        // 所以handler必须放最后一个

                        .addLast(new OrderFrameDecode())
                        .addLast(new OrderFrameEncoder())
                        .addLast(new OrderProtocolDecoder())
                        .addLast(new OrderProtocolEncoder())
                        // 此encoder将operation对象转化为RequestMsg对象
                        .addLast(new Operation2ReqMsgEncoder())
                        .addLast(new LoggingHandler(LogLevel.INFO));

            }
        });

        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8090);
        final OrderOperation operation = new OrderOperation(101, "番茄牛腩");

        channelFuture.sync();

        channelFuture.channel().writeAndFlush(operation);

        channelFuture.channel().closeFuture().get();
    }
}
