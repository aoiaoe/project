package com.cz.springbootredis.controller;

import com.cz.springbootredis.config.RateLimiter;
import com.cz.springbootredis.enums.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @RateLimiter(time = 5, count = 3, limitType = LimitType.IP)
    public String hello() {
        return "hello>>>" + new Date();
    }
}