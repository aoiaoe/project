package com.cz.learnnetty.chatroom.client.command;

import com.cz.learnnetty.chatroom.message.ChatRequestMessage;
import io.netty.channel.ChannelHandlerContext;

public class SendCommand extends Command{


    @Override
    public String cmd() {
        return Send;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, String[] cmds) {
        if(cmds != null && cmds.length == 3){
            ChatRequestMessage chatRequestMessage = new ChatRequestMessage(cmds[1], cmds[2]);
            ctx.writeAndFlush(chatRequestMessage);
        }
    }
}
