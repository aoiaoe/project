package com.cz.springcloudconsumerfeignhystrixstream.controller;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloud.entity.User;
import com.cz.springcloudconsumerfeignhystrixstream.service.EntitySerivce;
import com.cz.springcloudconsumerfeignhystrixstream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/consume")
@RestController
public class MyController {

    @Autowired
    private UserService userService;
    @Autowired
    private EntitySerivce entitySerivce;


    @GetMapping(value = "/entity/{id}")
    public Entity getById(@PathVariable("id") Integer id){
        return this.entitySerivce.getById(id);
    }

    @GetMapping(value = "/user/{id}")
    public User getById(@PathVariable("id") Long id){
        return this.userService.getById(id);
    }


}
