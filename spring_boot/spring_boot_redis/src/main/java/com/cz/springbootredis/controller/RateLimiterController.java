package com.cz.springbootredis.controller;

import com.cz.springbootredis.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    @Autowired
    private RateLimitService rateLimitService;

    @GetMapping(value = "/testAccess")
    public boolean access(){
        return this.rateLimitService.access();
    }
}
