package com.jzm.im;

import com.jzm.im.chat.ChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ImServerApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(ImServerApplication.class, args);

        startChatServer();
    }

    private static void startChatServer() {
        ChatServer chatServer = applicationContext.getBean(ChatServer.class);
        chatServer.startChatServer();
    }
}
