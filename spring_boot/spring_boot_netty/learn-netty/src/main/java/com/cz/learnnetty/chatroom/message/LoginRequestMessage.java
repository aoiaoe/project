package com.cz.learnnetty.chatroom.message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class LoginRequestMessage extends Message{

    private String userName;
    private String password;
    @Override
    public int getMsgType() {
        return LoginRequestMessage;
    }
}
