package com.cz.learnnetty.chatroom.client.command;

import com.cz.learnnetty.chatroom.message.GroupCreateRequestMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

public class GcreateCommand extends Command{
    @Override
    public String cmd() {
        return Gcreate;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, String[] cmds) {
        if (cmds != null && cmds.length > 1){
            GroupCreateRequestMessage msg = new GroupCreateRequestMessage(cmds[1], Arrays.asList(Arrays.copyOfRange(cmds, 2, cmds.length)));
            ctx.writeAndFlush(msg);
        }
    }

    public static void main(String[] args) {
        String cmds[] = {"gcreate", "gourp1", "u1"};
        System.out.println(Arrays.asList(Arrays.copyOfRange(cmds, 2, cmds.length)));
    }
}
