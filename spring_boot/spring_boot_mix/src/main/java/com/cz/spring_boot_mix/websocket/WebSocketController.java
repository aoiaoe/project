package com.cz.spring_boot_mix.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/msg/{username}")
public class WebSocketController {

    private static Map<String, Session> sessions = new ConcurrentHashMap();
    private static Map<Session, String> sessionName = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam(value = "username") String userName, Session session){
        sessions.put(userName, session);
        sessionName.put(session, userName);
        log.info("链接建立");
    }

    @OnMessage
    public void onMessage(Session session, String msg) throws IOException {
        String[] split = msg.split("#");
        Session target = sessions.get(split[0]);
        if(target == null){
            log.info("用户不在线");
            return;
        }
        String name = sessionName.get(session);
        String send = name + "#" + split[1];
        log.info("发送消息:{}", send);
        target.getBasicRemote().sendText(send);
    }

    @OnClose
    public void onClose(Session session){
        String name = sessionName.remove(session);
        sessions.remove(name);
        log.info("链接关闭");
    }
}
