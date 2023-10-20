package com.cz.learnnetty.chatroom.message;

import lombok.Data;

@Data
public class ChatResponseMessage extends Message{

    private String from;
    private String msg;

    @Override
    public int getMsgType() {
        return ChatResponseMessage;
    }
}
