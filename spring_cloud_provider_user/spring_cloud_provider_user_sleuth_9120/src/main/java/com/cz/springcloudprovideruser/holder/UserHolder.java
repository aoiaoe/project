package com.cz.springcloudprovideruser.holder;

import com.cz.springcloud.entity.User;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class UserHolder{

    @Value("${server.port}")
    private Integer port;

    private volatile Map<Long, User> users;

    private UserHolder(){}

    public Map<Long, User> getHolder(){
       return users;
    }

    @PostConstruct
    public void init(){
        users = ImmutableMap.of(1L, new User(1L, "张三", port.toString()), 2L, new User(2L, "李四", port.toString()));
    }
}
