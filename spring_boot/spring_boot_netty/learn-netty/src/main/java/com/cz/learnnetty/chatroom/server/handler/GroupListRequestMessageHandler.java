package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.GroupListRequestMessage;
import com.cz.learnnetty.chatroom.message.GroupListResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.session.Group;
import com.cz.learnnetty.chatroom.server.session.GroupSession;
import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class GroupListRequestMessageHandler implements RequestHandler {

    private Session session;
    private GroupSession groupSession;

    public GroupListRequestMessageHandler(Session session, GroupSession groupSession){
        this.session = session;
        this.groupSession = groupSession;
    }

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        GroupListResponseMessage response = new GroupListResponseMessage();
        try {
            String userName = session.getName(ctx.channel());
            List<Group> groupList = groupSession.userGroup(userName);
            response.setSuccess(true);
//            List<Long> groupIds = Optional.ofNullable(groupList).orElse(new ArrayList<>()).stream().map(e -> e.getGroupId()).collect(Collectors.toList());
            response.setGroupList(groupList);
        } catch (Exception e){
            log.error("获取用户所在群组失败:{}", e);
            response.setSuccess(false);
            response.setMsg("服务器错误");
        }
        ctx.writeAndFlush(response);
    }
}
