package com.cz.learnnetty.chatroom.client.command;

import com.cz.learnnetty.chatroom.message.GroupChatRequestMessage;
import io.netty.channel.ChannelHandlerContext;

public class GsendCommand extends Command{
    @Override
    public String cmd() {
        return Gsend;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, String[] cmds) {
        GroupChatRequestMessage msg = new GroupChatRequestMessage();
        msg.setGroupId(Long.valueOf(cmds[1]));
        msg.setMessage(cmds[2]);
        ctx.writeAndFlush(msg);

    }
}
