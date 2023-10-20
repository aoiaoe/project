package com.cz.learnnetty.chatroom.client.handler;

import com.cz.learnnetty.chatroom.message.GroupChatResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.handler.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupChatResponseMessageHandler implements RequestHandler {
    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        GroupChatResponseMessage msg = (GroupChatResponseMessage)message;
        log.info("群组id:{}, 群组名称:{}, 用户:{}, 发来一条消息:{}", msg.getGroup(), msg.getGroupName(), msg.getUser(), msg.getMsg());
    }
}
