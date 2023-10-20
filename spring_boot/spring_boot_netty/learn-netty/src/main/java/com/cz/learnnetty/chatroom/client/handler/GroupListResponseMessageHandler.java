package com.cz.learnnetty.chatroom.client.handler;

import com.cz.learnnetty.chatroom.message.GroupListResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.handler.RequestHandler;
import com.cz.learnnetty.chatroom.server.session.Group;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GroupListResponseMessageHandler implements RequestHandler {

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        GroupListResponseMessage msg = (GroupListResponseMessage)message;
        if(msg.isSuccess()){
            List<Group> groupList = msg.getGroupList();
            if(groupList == null || groupList.isEmpty()){
              log.info("用户暂无群组");
              return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("用户群组列表:\r\n");
            for (Group group : msg.getGroupList()) {
                sb.append("群组id:\t").append(group.getGroupId());
                sb.append("\t群组名称:\t").append(group.getName());
                sb.append("\t群组用户:\t").append(group.getUsers());
                sb.append("\r\n");
            }
//            List<Long> groupIds = msg.getGroupList();
//            if(groupIds == null || groupIds.isEmpty()){
//              log.info("用户暂无群组");
//              return;
//            }
//            StringBuilder sb = new StringBuilder();
//            sb.append("用户群组列表:\r\n");
//            for (Long group : groupIds) {
//                sb.append("群组id:\t").append(group);
//                sb.append("\r\n");
//            }
            log.info("{}",sb.toString());
        } else {
            log.info("获取用户群组失败, error:{}", msg.getMsg());
        }
    }
}
