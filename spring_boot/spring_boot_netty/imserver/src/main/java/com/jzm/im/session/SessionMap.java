package com.jzm.im.session;

import com.jzm.common.bean.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionMap {

    private SessionMap(){}

    private static SessionMap singleton = new SessionMap();
    public static SessionMap instance(){
        return singleton;
    }

//    会话集合
    private ConcurrentHashMap<String, ServerSession> map =
            new ConcurrentHashMap<String, ServerSession>();
    private ConcurrentHashMap<String, ServerSession> userMap =
            new ConcurrentHashMap<String, ServerSession>();

    public void registerSession(ServerSession session) {
        map.put(session.getSessionId(), session);
        userMap.put(session.getUser().getUid(), session);
        log.info("用户id:{}上线, 在线总数:{}", session.getUser().getUid(), map.size());
    }

    public void removeSession(String sessionId) {
        ServerSession session = map.remove(sessionId);
        Optional.ofNullable(session)
                .ifPresent(s -> {
                    Optional.ofNullable(s.getUser())
                            .ifPresent(u -> {
                               userMap.remove(u.getUid());
                            });
                    log.info("用户id:{}下线, 在线总数:{}", session.getUser().getUid(), map.size());
                });
    }
    public boolean hasLogin(Users user) {
        Iterator<Map.Entry<String, ServerSession>> it =
                map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ServerSession> next = it.next();
            Users u = next.getValue().getUser();
            if (u.getUid().equals(user.getUid())
                    && u.getPlatform().equals(user.getPlatform())) {
                return true;
            }
        }

        return false;
    }

    public ServerSession getSessionBy(String to) {
        return userMap.get(to);
    }
}
