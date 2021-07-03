package com.cz.springcloudprovideruser.service.impl;

import com.cz.springcloud.entity.User;
import com.cz.springcloudprovideruser.holder.UserHolder;
import com.cz.springcloudprovideruser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserHolder userHolder;

    @Override
    public List<User> findAll() {
        Map<Long, User> holder = userHolder.getHolder();
        if (holder != null && !holder.isEmpty()) {
            return new ArrayList<>(holder.values());
        }
        return null;
    }

    @Override
    public User get(Long id) {
        Map<Long, User> holder = userHolder.getHolder();
        if (holder != null && !holder.isEmpty()) {
            return holder.get(id);
        }
        return null;
    }
}
