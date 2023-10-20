package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.ChatRequestMessage;
import com.cz.learnnetty.chatroom.message.ChatResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatRequestMessageHandler implements RequestHandler{

    private Session session;

    @Override
    public void handler(ChannelHandlerContext ctx, Message message) throws Exception {
        ChatRequestMessage chatRequestMessage = (ChatRequestMessage)message;
        Channel channel = session.getChannel(((ChatRequestMessage) message).getTo());
        if(channel != null){
            ChatResponseMessage chatResponseMessage = new ChatResponseMessage();
            chatResponseMessage.setFrom(session.getName(ctx.channel()));
            chatResponseMessage.setMsg(chatRequestMessage.getMsg());
            channel.writeAndFlush(chatResponseMessage);
        }
    }
}
