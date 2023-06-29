package com.cz.springcloudprovideruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/demo")
    public String demo(){
        return "hello world! " + System.currentTimeMillis();
    }
}
