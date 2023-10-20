package com.cz.learnnetty.chatroom.client.command;

import java.util.HashMap;
import java.util.Map;

import static com.cz.learnnetty.chatroom.client.command.Command.*;


public class CommandFactory {

    private static Map<String, Command> CMDS = new HashMap<>();

    public CommandFactory(){
        CMDS.put(Send, new SendCommand());
        CMDS.put(Glist, new GlistCommand());
        CMDS.put(Gcreate, new GcreateCommand());
        CMDS.put(Gsend, new GsendCommand());
    }

    public Command detectCmd(String cmd){
        return CMDS.get(cmd);
    }
}
