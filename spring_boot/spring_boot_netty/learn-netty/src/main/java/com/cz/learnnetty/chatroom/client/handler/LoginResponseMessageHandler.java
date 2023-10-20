package com.cz.learnnetty.chatroom.client.handler;

import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.handler.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginResponseMessageHandler implements RequestHandler {

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        log.info("登录成功");
    }
}
