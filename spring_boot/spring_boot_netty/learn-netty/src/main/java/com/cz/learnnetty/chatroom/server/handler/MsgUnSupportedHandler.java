package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.message.UnSupportedResponseMessage;
import io.netty.channel.ChannelHandlerContext;

public class MsgUnSupportedHandler implements RequestHandler {
    private UnSupportedResponseMessage message = new UnSupportedResponseMessage();
    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        ctx.writeAndFlush(message);
    }
}
