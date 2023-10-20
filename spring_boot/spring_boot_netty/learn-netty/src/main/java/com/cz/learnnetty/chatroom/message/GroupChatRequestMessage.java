package com.cz.learnnetty.chatroom.message;

import lombok.Data;

@Data
public class GroupChatRequestMessage extends Message{

    private Long groupId;
    private String message;

    @Override
    public int getMsgType() {
        return GroupChatRequestMessage;
    }
}
