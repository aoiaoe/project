package com.cz.learnnetty.chatroom.message;

public class GroupListRequestMessage extends Message{
    @Override
    public int getMsgType() {
        return GroupListRequestMessage;
    }
}
