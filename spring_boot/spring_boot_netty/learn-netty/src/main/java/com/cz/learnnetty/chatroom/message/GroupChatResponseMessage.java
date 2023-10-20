package com.cz.learnnetty.chatroom.message;

import lombok.Data;

@Data
public class GroupChatResponseMessage extends AbstractResponseMessage{

    private Long group;
    private String groupName;
    private String user;
    private String msg;
    @Override
    public int getMsgType() {
        return GroupChatResponseMessage;
    }
}
