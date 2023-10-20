package com.cz.learnnetty.chatroom.message;

public class UnSupportedResponseMessage extends Message{

    @Override
    public int getMsgType() {
        return UnSupportedResponseMessage;
    }
}
