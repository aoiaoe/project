package com.cz.learnnetty.chatroom.message;

import com.cz.learnnetty.rpc.message.RpcResponseMessage;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int seqId;
    private int msgId;

    public abstract int getMsgType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupQuitRequestHessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int GroupListRequestMessage = 14;
    public static final int GroupListResponseMessage = 15;

    // rpc
    public static final int RpcRequestMessage = 200;
    public static final int RpcResponseMessage = 201;

    public static final int PingMessage = 10000;

    public static final int UnSupportedResponseMessage = 99;
    private static final Map<Integer, Class> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage, ChatRequestMessage.class);
        messageClasses.put(ChatResponseMessage, ChatResponseMessage.class);
        messageClasses.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
//        messageClasses.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
//        messageClasses.put(GroupJoinResponseMessage =);
//        messageClasses.put(GroupQuitRequestHessage = );
//        messageClasses.put(GroupQuitResponseMessage =);
        messageClasses.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
        messageClasses.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
//        messageClasses.put(GroupMembersRequestMessage, GroupMembersRequestMessage.class);
//        messageClasses.put(GroupMembersResponseMessag);
        messageClasses.put(GroupListRequestMessage, GroupListRequestMessage.class);
        messageClasses.put(GroupListResponseMessage, GroupListResponseMessage.class);
        messageClasses.put(RpcRequestMessage, com.cz.learnnetty.rpc.message.RpcRequestMessage.class);
        messageClasses.put(RpcResponseMessage, com.cz.learnnetty.rpc.message.RpcResponseMessage.class);
    }
}
