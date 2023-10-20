package com.cz.learnnetty.chatroom.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GroupCreateRequestMessage extends Message{

    private String name;
    private List<String> users;

    @Override
    public int getMsgType() {
        return GroupCreateRequestMessage;
    }
}
