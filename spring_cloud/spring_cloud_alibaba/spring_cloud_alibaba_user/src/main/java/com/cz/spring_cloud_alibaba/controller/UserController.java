package com.cz.spring_cloud_alibaba.controller;

import com.cz.spring_cloud_alibaba.domain.UserDto;
import com.cz.spring_cloud_alibaba.domain.UserVo;
import com.cz.spring_cloud_alibaba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserVo user(Long id){
        return this.userService.user(id);
    }

    @GetMapping(value = "/{id}")
    public UserVo users(@PathVariable Long id){
        return this.userService.userInfo(id);
    }

    @PostMapping(value = "/add")
    public boolean createUser(@RequestBody UserDto param){
        return this.userService.addUser(param);
    }
}
