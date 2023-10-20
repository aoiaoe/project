package com.cz.learnnetty.chatroom.message;

import lombok.Data;

@Data
public class GroupCreateResponseMessage extends AbstractResponseMessage{

    private Long groupId;

    @Override
    public int getMsgType() {
        return GroupCreateResponseMessage;
    }
}
