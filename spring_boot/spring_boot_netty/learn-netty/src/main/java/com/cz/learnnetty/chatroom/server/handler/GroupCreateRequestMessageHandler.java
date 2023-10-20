package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.GroupCreateRequestMessage;
import com.cz.learnnetty.chatroom.message.GroupCreateResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.session.Group;
import com.cz.learnnetty.chatroom.server.session.GroupSession;
import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@Slf4j
public class GroupCreateRequestMessageHandler implements RequestHandler{

    public Session session;
    public GroupSession groupSession;
    public GroupCreateRequestMessageHandler(Session session, GroupSession groupSession){
        this.session = session;
        this.groupSession = groupSession;
    }

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        GroupCreateResponseMessage responseMessage = new GroupCreateResponseMessage();
        try {
            GroupCreateRequestMessage groupCreateRequestMessage = (GroupCreateRequestMessage)message;
            HashSet<String> users = new HashSet<>(groupCreateRequestMessage.getUsers());
            users.add(session.getName(ctx.channel()));
            Group group = groupSession.createGroup(groupCreateRequestMessage.getName(), users);
            if(group != null){
                responseMessage.setGroupId(group.getGroupId());
                responseMessage.setSuccess(true);
            }
        }catch (Exception e){
            log.error("创建群组失败:{}", e);
        }
        ctx.writeAndFlush(responseMessage);
    }
}
