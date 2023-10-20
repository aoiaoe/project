package com.cz.learnnetty.chatroom.server.session;

import io.netty.channel.Channel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MemoryGroupSession implements GroupSession{

    private Map<Long, Group> GROUP = new ConcurrentHashMap<>();
    private Map<String, List<Group>> USER_GROUP = new ConcurrentHashMap();
    private AtomicLong atomicLong = new AtomicLong();


    @Override
    public Group createGroup(String name, Set<String> members) {
        Group group = new Group(atomicLong.incrementAndGet(), name, members);
        GROUP.put(group.getGroupId(), group);
        for (String member : members) {
            USER_GROUP.computeIfAbsent(member, e -> new CopyOnWriteArrayList<>()).add(group);
        }
        return group;
    }

    @Override
    public Group join(Long gourpId, String userName) {
        return null;
    }

    @Override
    public void remove(Long groupId, String userName) {

    }

    @Override
    public void deleteGroup(Long groupId) {

    }

    @Override
    public Set<String> getMembers(Long groupId) {
        return null;
    }

    @Override
    public List<Channel> getMemberChannel(Session session, Long groupId) {
        Group group = findGroup(groupId);
        return Optional.ofNullable(group.getUsers()).orElse(new HashSet<>())
                .stream().map(e -> {
            return session.getChannel(e);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Group> userGroup(String userName) {
        return USER_GROUP.get(userName);
    }

    @Override
    public Group findGroup(Long groupId) {
        return GROUP.get(groupId);
    }
}
