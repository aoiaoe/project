package com.cz.learnnetty.chatroom.server.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

public interface GroupSession {

    Group createGroup(String name, Set<String> members);

    Group join(Long gourpId, String userName);

    void remove(Long groupId, String userName);

    void deleteGroup(Long groupId);

    Set<String> getMembers(Long groupId);

    List<Channel> getMemberChannel(Session session, Long groupId);

    List<Group> userGroup(String userName);

    Group findGroup(Long groupId);
}
