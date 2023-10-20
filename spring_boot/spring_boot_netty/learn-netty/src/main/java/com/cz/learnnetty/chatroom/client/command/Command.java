package com.cz.learnnetty.chatroom.client.command;

import io.netty.channel.ChannelHandlerContext;

public abstract class Command {

    public static String Send = "send";
    public static String Glist = "glist";
    public static String Gsend= "gsend";
    public static String Gcreate= "gcreate";
    public static String Gmem = "gmem";
    public static String Gjoin = "gjoin";
    public static String Gquit = "gquit";
    public static String Quit = "quit";



    public abstract String cmd();

    public abstract void handle(ChannelHandlerContext ctx, String[] cmds);

}
