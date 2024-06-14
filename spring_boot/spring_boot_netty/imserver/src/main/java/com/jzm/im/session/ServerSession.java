package com.jzm.im.session;

import com.jzm.common.bean.Users;
import com.jzm.common.bean.msg.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Data
public class ServerSession {

    public static final AttributeKey<String> KEY_USER_Id = AttributeKey.valueOf("key_user_id");
    public static final AttributeKey<ServerSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");


    private Channel channel;
    private Users user;
    private final String sessionId;

    private boolean login = false;


    public ServerSession(Channel channel){
        this.channel = channel;
        sessionId = buildNewSessionId();
    }
    public static void closeSession(ChannelHandlerContext ctx) {
        ServerSession session = ctx.channel().attr(SESSION_KEY).get();
        if(session != null && session.isValid()){
            session.channel.close();
            SessionMap.instance().removeSession(session.getSessionId());
        }
    }

    private static String buildNewSessionId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public boolean isValid() {
        return getUser() != null ? true : false;
    }

    public void writeAndFlush(ProtoMsg.Message response) {
        //当系统水位过高时，系统应不继续发送消息，防止发送队列积压
        //写Protobuf数据帧

        if (channel.isWritable()) //低水位
        {
            channel.writeAndFlush(response);
        } else {   //高水位时
            log.debug("通道很忙，消息被暂存了");
            //写入消息暂存的分布式存储，如果mongo
            //等channel空闲之后，再写出去
        }

    }

    public ServerSession reverseBind() {
        channel.attr(SESSION_KEY).set(this);
        SessionMap.instance().registerSession(this);
        login = true;
        return this;
    }
}
