package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.Message;
import io.netty.channel.ChannelHandlerContext;

public interface RequestHandler {

    void handler(ChannelHandlerContext ctx, Message message) throws Exception;
}
