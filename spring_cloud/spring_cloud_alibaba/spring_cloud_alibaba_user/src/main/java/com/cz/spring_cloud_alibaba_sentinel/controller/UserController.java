package com.cz.spring_cloud_alibaba_sentinel.controller;

import com.cz.spring_cloud_alibaba_sentinel.domain.UserVo;
import com.cz.spring_cloud_alibaba_sentinel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserVo user(Long id){
        return this.userService.user(id);
    }

}
