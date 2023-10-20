package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class ChannelUnbindHandler extends ChannelInboundHandlerAdapter {
        private final Session session;

        public ChannelUnbindHandler(Session session) {
            this.session = session;
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("{}已经断开", ctx.channel());
            session.unbind(ctx.channel());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.info("{}已经异常断开, 异常:{}", ctx.channel(), cause);
            session.unbind(ctx.channel());
        }
    }