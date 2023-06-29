package com.cz.springcloudconsumerfeign.feign;

import com.cz.springcloud.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFeignFallback implements UserClient{
    @Override
    public List<User> findAll() {
        return new ArrayList<>();
    }

    @Override
    public User get(Long id) {
        return new User(-1L, "mock", "mock");
    }
}
