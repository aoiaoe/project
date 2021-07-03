package com.cz.spring_boot_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/admin/hello")
    public String adminHello() {
        return "admin hello";
    }

    @GetMapping(value = "/user/hello")
    public String userHello() {
        return "user hello";
    }
}
