package com.cz.learnnetty.chatroom.message;

public class PingMessage extends Message{
    @Override
    public int getMsgType() {
        return PingMessage;
    }
}
