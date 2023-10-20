package com.cz.learnnetty.chatroom.server.service;

import com.cz.learnnetty.chatroom.message.LoginRequestMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginService {

    static Map<String, String> USER = new HashMap<>();
    static {
        USER.put("JZM", "123456");
        USER.put("admin", "admin");
        USER.put("jzm", "123456");
        USER.put("a", "a");
        USER.put("b", "b");
        USER.put("c", "c");
        USER.put("d", "d");
    }

    public boolean login(LoginRequestMessage message){
        return Objects.equals(message.getPassword(), USER.get(message.getUserName()));
    }
}
