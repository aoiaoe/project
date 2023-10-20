package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@ChannelHandler.Sharable
public class BizHandler extends SimpleChannelInboundHandler<Message> {

    private RequestHandlerFactory factory;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        RequestHandler handler = factory.detectHandler(msg.getMsgType());
        if(handler == null){
            return;
        }
        handler.handler(ctx, msg);
    }
}
