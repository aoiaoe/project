package com.cz.learnnetty.chatroom.server.handler;


import com.cz.learnnetty.chatroom.client.handler.*;
import com.cz.learnnetty.chatroom.server.session.GroupSession;
import com.cz.learnnetty.chatroom.server.session.Session;

import java.util.HashMap;
import java.util.Map;

import static com.cz.learnnetty.chatroom.message.Message.*;

public class RequestHandlerFactory {


    static Map<Integer, RequestHandler> HANDLER_FACTORY = new HashMap<>();
    public static RequestHandler DEFAULT_HANDLER = new MsgUnSupportedHandler();

    private Session session;
    public RequestHandlerFactory(){
        init4Client();
    }
    public RequestHandlerFactory(Session session, GroupSession groupSession){
        this.session = session;
        assert session != null;
        init4Server(session, groupSession);
    }

    public void init4Server(Session session, GroupSession groupSession){
        HANDLER_FACTORY.put(ChatRequestMessage, new ChatRequestMessageHandler(session));
        HANDLER_FACTORY.put(GroupCreateRequestMessage, new GroupCreateRequestMessageHandler(session, groupSession));
        HANDLER_FACTORY.put(GroupListRequestMessage, new GroupListRequestMessageHandler(session, groupSession));
        HANDLER_FACTORY.put(GroupChatRequestMessage, new GroupChatRequestMessageHandler(session, groupSession));
    }

    public void init4Client(){
//        HANDLER_FACTORY.put(LoginResponseMessage, new LoginResponseMessageHandler());
        HANDLER_FACTORY.put(ChatResponseMessage, new ChatResponseMessageHandler());
        HANDLER_FACTORY.put(GroupCreateResponseMessage, new GroupCreateResponseMessageHandler());
        HANDLER_FACTORY.put(GroupListResponseMessage, new GroupListResponseMessageHandler());
        HANDLER_FACTORY.put(GroupChatResponseMessage, new GroupChatResponseMessageHandler());
    }

    public RequestHandler detectHandler(int type){
//        return HANDLER_FACTORY.getOrDefault(type, DEFAULT_HANDLER);
        return HANDLER_FACTORY.get(type);
    }
}
