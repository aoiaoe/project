package com.cz.springcloudprovideruser9106.controller;

import com.cz.springcloud.api.UserApi;
import com.cz.springcloud.entity.User;
import com.cz.springcloudprovideruser9106.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @Override
    public User get(Long id) {
        return this.userService.get(id);
    }
}
