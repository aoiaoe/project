package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.GroupChatRequestMessage;
import com.cz.learnnetty.chatroom.message.GroupChatResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.session.Group;
import com.cz.learnnetty.chatroom.server.session.GroupSession;
import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GroupChatRequestMessageHandler implements RequestHandler {

    private Session session;
    private GroupSession groupSession;

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        GroupChatRequestMessage msg = (GroupChatRequestMessage)message;
        Channel curChannel = ctx.channel();
        GroupChatResponseMessage responseMessage = new GroupChatResponseMessage();
        responseMessage.setUser(session.getName(curChannel));
        responseMessage.setMsg(msg.getMessage());
        Group group = groupSession.findGroup(msg.getGroupId());
        if(group == null){
            return;
        }
        responseMessage.setGroup(group.getGroupId());
        responseMessage.setGroupName(group.getName());
        List<Channel> channelList = groupSession.getMemberChannel(session, msg.getGroupId());
        if(channelList == null){
            return;
        }
        for (Channel channel : channelList) {
            if(channel != curChannel){
                channel.writeAndFlush(responseMessage);
            }
        }

    }
}
