package com.cz.learnnetty.chatroom.client.handler;

import com.cz.learnnetty.chatroom.message.ChatResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.handler.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatResponseMessageHandler implements RequestHandler {
    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        ChatResponseMessage chatResponseMessage = (ChatResponseMessage) message;
        log.info("用户:{}, 给你发送了一条消息:{}", chatResponseMessage.getFrom(), chatResponseMessage.getMsg());
    }
}
