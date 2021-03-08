package com.cz.springcloudprovideruser.controller;

import com.cz.springcloud.api.UserApi;
import com.cz.springcloud.entity.User;
import com.cz.springcloudprovideruser.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有用户")
    @Override
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @ApiOperation(value = "获取指定用户")
    @Override
    public User get(Long id) {
        return this.userService.get(id);
    }
}
