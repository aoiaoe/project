package com.cz.learnnetty.chatroom.server;

import com.cz.learnnetty.chatroom.code.MessageCodec;
import com.cz.learnnetty.chatroom.protocol.ProtocolFrameDecoder;
import com.cz.learnnetty.chatroom.protocol.Serializer;
import com.cz.learnnetty.chatroom.server.handler.BizHandler;
import com.cz.learnnetty.chatroom.server.handler.ChannelUnbindHandler;
import com.cz.learnnetty.chatroom.server.handler.LoginRequestMessageHandler;
import com.cz.learnnetty.chatroom.server.handler.RequestHandlerFactory;
import com.cz.learnnetty.chatroom.server.service.LoginService;
import com.cz.learnnetty.chatroom.server.session.GroupSession;
import com.cz.learnnetty.chatroom.server.session.MemoryGroupSession;
import com.cz.learnnetty.chatroom.server.session.MemorySession;
import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    static NioEventLoopGroup boss = null;
    static NioEventLoopGroup worker = null;
    public static void main(String[] args) throws InterruptedException {
        try {
            ServerBootstrap server = new ServerBootstrap();
            boss = new NioEventLoopGroup(1);
            worker = new NioEventLoopGroup();
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
            LoginService loginService = new LoginService();
            Session session = new MemorySession();
            GroupSession groupSession = new MemoryGroupSession();
            RequestHandlerFactory factory = new RequestHandlerFactory(session, groupSession);
            BizHandler bizHandler = new BizHandler(factory);
            ChannelUnbindHandler channelUnbindHandler = new ChannelUnbindHandler(session);
            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("protocol", new ProtocolFrameDecoder())
                                    .addLast("loggingHandler", loggingHandler)
                                    .addLast("messageCodec", new MessageCodec(Serializer.Algorithm.JSON))
                                    // 用来判断是否读/写/读写空闲过长
                                    // 5秒内没收到channel数据, 会触发一个IdleState.READER_IDLE事件
                                    .addLast("idleChecker", new IdleStateHandler(5, 0, 0))
                                    .addLast("idleEventHandler", new ChannelDuplexHandler(){
                                        // 处理特殊事件
                                        @Override
                                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                            IdleStateEvent event = (IdleStateEvent) evt;
                                            if(event.state() == IdleState.READER_IDLE){
                                                log.debug("已经5秒没读到数据了, {}", ctx.channel());
                                            }
                                        }
                                    })
                                    .addLast("loginHandler", new LoginRequestMessageHandler(loginService, session))
                                    // 连接关闭时,清理channel缓存
                                    .addLast("logoutHandler", channelUnbindHandler)
                                    // 业务处理器
                                    .addLast("bizHandler", bizHandler)
                                    .addLast();
                        }
                    });
            ChannelFuture channelFuture = server.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
