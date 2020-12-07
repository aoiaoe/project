package com.cz.springcloudprovideruser9106.service.impl;

import com.cz.springcloud.entity.User;
import com.cz.springcloudprovideruser9106.service.UserService;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${server.port}")
    private Integer port;

    @Override
    public List<User> findAll() {
        return ImmutableList.of(new User(1L, "李四", port.toString()));
    }

    @Override
    public User get(Long id) {
        return new User(1L, "李四", port.toString());
    }
}
