package com.cz.learnnetty.chatroom.server.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemorySession implements Session{

    Map<String, Channel> SESSION = new ConcurrentHashMap<>();
    Map<Channel, String> SESSION_REVERSE = new ConcurrentHashMap<>();
    Map<Channel, Map<String, Object>> ATTR = new ConcurrentHashMap<>();

    Map EMPTY = new HashMap();

    @Override
    public void bind(Channel channel, String userName) {
        SESSION.putIfAbsent(userName,channel);
        SESSION_REVERSE.putIfAbsent(channel, userName);
        ATTR.put(channel, new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        String remove = SESSION_REVERSE.remove(channel);
        if(remove != null) {
            SESSION.remove(remove);
        }
        ATTR.remove(channel);
    }

    @Override
    public Object getAttr(Channel channel, String name) {
        return ATTR.getOrDefault(channel, EMPTY).get(name);
    }

    @Override
    public void setAttr(Channel channel, String name, Object value) {
        ATTR.computeIfAbsent(channel, map ->  new ConcurrentHashMap<>()).put(name, value);
    }

    @Override
    public Channel getChannel(String userName) {
        return SESSION.get(userName);
    }

    @Override
    public String getName(Channel channel) {
        return SESSION_REVERSE.get(channel);
    }
}
