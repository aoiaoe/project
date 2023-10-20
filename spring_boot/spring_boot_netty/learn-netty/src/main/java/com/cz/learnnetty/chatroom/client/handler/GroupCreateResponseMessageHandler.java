package com.cz.learnnetty.chatroom.client.handler;

import com.cz.learnnetty.chatroom.message.GroupCreateResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.handler.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupCreateResponseMessageHandler implements RequestHandler {

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        GroupCreateResponseMessage msg = (GroupCreateResponseMessage)message;
        StringBuilder sb = new StringBuilder("创建群组");
        if(msg.isSuccess()){
            sb.append("成功,群组id:").append(msg.getGroupId());
        } else {
            sb.append("失败,请确认后重试, 错误原因:").append(msg.getMsg());
        }
        log.info("{}", sb.toString());
    }
}
