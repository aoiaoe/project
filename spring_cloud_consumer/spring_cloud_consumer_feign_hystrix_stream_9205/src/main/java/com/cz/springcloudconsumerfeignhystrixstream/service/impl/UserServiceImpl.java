package com.cz.springcloudconsumerfeignhystrixstream.service.impl;

import com.cz.springcloud.entity.User;
import com.cz.springcloudconsumerfeignhystrixstream.feign.UserFeignClient;
import com.cz.springcloudconsumerfeignhystrixstream.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public User getById(Long id) {
        return this.userFeignClient.get(id);
    }
}
