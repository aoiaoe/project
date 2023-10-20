package com.cz.learnnetty.chatroom.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRequestMessage extends Message{

    private String to;
    private String msg;

    @Override
    public int getMsgType() {
        return ChatRequestMessage;
    }
}
