package com.cz.learnnetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class LearnNettyApplicationTests {

    @Test
    public void test() {
        System.out.println(NettyRuntime.availableProcessors());
    }

    @Test
    public void testNettyConnect2Redis() throws InterruptedException {
        final byte[] LINE = {13,10};
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler(LogLevel.DEBUG))
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("连接建立,发送数据");
                                            ByteBuf buffer = ctx.alloc().buffer();
                                            buffer.writeBytes("*3".getBytes());
                                            buffer.writeBytes(LINE);
                                            buffer.writeBytes("$3".getBytes());
                                            buffer.writeBytes(LINE);
                                            buffer.writeBytes("set".getBytes());
                                            buffer.writeBytes(LINE);
                                            buffer.writeBytes("$4".getBytes());
                                            buffer.writeBytes(LINE);
                                            buffer.writeBytes("name".getBytes());
                                            buffer.writeBytes(LINE);
                                            buffer.writeBytes("$3".getBytes());
                                            buffer.writeBytes(LINE);
                                            buffer.writeBytes("jzm".getBytes());
                                            buffer.writeBytes(LINE);
                                            ctx.writeAndFlush(buffer);

                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            ByteBuf byteBuf = (ByteBuf) msg;
                                            System.out.println("获取到服务端响应:" + byteBuf.toString());
                                        }
                                    });
                        }
                    }).connect("106.53.96.154", 6479)
                    .sync();
            ChannelFuture sync = channelFuture.channel().closeFuture().sync();
            sync.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("关闭连接");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
