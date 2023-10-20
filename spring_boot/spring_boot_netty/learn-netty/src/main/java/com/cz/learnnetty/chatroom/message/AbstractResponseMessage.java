package com.cz.learnnetty.chatroom.message;

import lombok.Data;

@Data
public abstract class AbstractResponseMessage extends Message{

    private boolean success;
    private String msg;
}
