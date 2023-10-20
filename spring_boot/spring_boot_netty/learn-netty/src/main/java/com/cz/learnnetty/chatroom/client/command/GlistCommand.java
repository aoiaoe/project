package com.cz.learnnetty.chatroom.client.command;

import com.cz.learnnetty.chatroom.message.GroupListRequestMessage;
import io.netty.channel.ChannelHandlerContext;

public class GlistCommand extends Command{
    @Override
    public String cmd() {
        return Glist;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, String[] cmds) {
        GroupListRequestMessage msg = new GroupListRequestMessage();
        ctx.writeAndFlush(msg);
    }
}
