package com.cz.grpcclient.controller;

import com.cz.grpcclient.service.MyGreeterGrpcClient;
import com.cz.grpcclient.service.UsersGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyGreeterController {

    @Autowired
    private MyGreeterGrpcClient myGreeterGrpcClient;

    @Autowired
    private UsersGrpcClient usersGrpcClient;

    @GetMapping(value = "/greet")
    public String greet(String name){
        return myGreeterGrpcClient.greet(name);
    }

    @GetMapping(value = "/userInfo")
    public boolean getUserInfo(){
        usersGrpcClient.userInfo(1L);
        return true;
    }
}
