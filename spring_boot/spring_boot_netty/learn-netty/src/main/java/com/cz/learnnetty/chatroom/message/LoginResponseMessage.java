package com.cz.learnnetty.chatroom.message;

import lombok.Data;

@Data
public class LoginResponseMessage extends AbstractResponseMessage{

    @Override
    public int getMsgType() {
        return LoginResponseMessage;
    }
}
