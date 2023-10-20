package com.cz.learnnetty.chatroom.server.session;


import io.netty.channel.Channel;

public interface Session {

    void bind(Channel channel, String userName);

    void unbind(Channel channel);

    Object getAttr(Channel channel, String userName);

    void setAttr(Channel channel, String userName, Object value);

    Channel getChannel(String userName);

    String getName(Channel channel);

}
