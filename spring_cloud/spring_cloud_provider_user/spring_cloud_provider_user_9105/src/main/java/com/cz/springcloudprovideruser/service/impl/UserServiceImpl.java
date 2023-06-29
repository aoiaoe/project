package com.cz.springcloudprovideruser.service.impl;

import com.cz.springcloud.entity.User;
import com.cz.springcloudprovideruser.service.UserService;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${server.port}")
    private Integer port;

    @Override
    public List<User> findAll() {
        return ImmutableList.of(new User(1L, "张三", port.toString()));
    }

    @Override
    public User get(Long id) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Map decodedDetails = (Map) details.getDecodedDetails();
        System.out.println(decodedDetails);
        return new User(1L, "张三", port.toString());
    }
}
