package com.cz.learnnetty.chatroom.client;

import com.cz.learnnetty.chatroom.client.command.CommandFactory;
import com.cz.learnnetty.chatroom.client.handler.BizLoginAndCommandHandler;
import com.cz.learnnetty.chatroom.code.MessageCodec;
import com.cz.learnnetty.chatroom.message.PingMessage;
import com.cz.learnnetty.chatroom.protocol.ProtocolFrameDecoder;
import com.cz.learnnetty.chatroom.protocol.Serializer;
import com.cz.learnnetty.chatroom.server.handler.BizHandler;
import com.cz.learnnetty.chatroom.server.handler.RequestHandlerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class ChatClient {

    static NioEventLoopGroup worker = null;
    public static void main(String[] args) {
        try {
            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
            RequestHandlerFactory factory = new RequestHandlerFactory();
            BizHandler bizHandler = new BizHandler(factory);
            CommandFactory commandFactory = new CommandFactory();
            BizLoginAndCommandHandler bizLoginAndCommandHandler = new BizLoginAndCommandHandler(commandFactory);
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("protocol", new ProtocolFrameDecoder())
                                    .addLast("loggingHandler", loggingHandler)
                                    // 使用jdk序列化
                                    .addLast("messageCodec", new MessageCodec(Serializer.Algorithm.JSON))
                                    // 用来判断是否读/写/读写空闲过长
                                    // 3秒内没写数据, 会触发一个 IdleState.WRITER_IDLE 事件
                                    .addLast("idleChecker", new IdleStateHandler(0, 3, 0))
                                    .addLast("idleEventHandler", new ChannelDuplexHandler(){
                                        // 处理特殊事件
                                        @Override
                                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                            if(evt instanceof IdleStateEvent) {
                                                IdleStateEvent event = (IdleStateEvent) evt;
                                                if (event.state() == IdleState.WRITER_IDLE) {
//                                                    log.debug("已经3秒没写数据了, 发送一个心跳包");
                                                    // 写一个心跳包给服务端
//                                                    ctx.writeAndFlush(new PingMessage());
                                                }
                                            }
//                                            super.userEventTriggered(ctx, evt);
                                        }
                                    })
                                    .addLast("bizLogAndCmd", bizLoginAndCommandHandler)
                                    .addLast("bizHandler", bizHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    log.info("回调关闭连接");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }
}
