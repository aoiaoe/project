package com.cz.spring_boot_sentry.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/sentry")
@RestController
public class SentryController {

    @GetMapping(value = "/send")
    public boolean demo(Integer value){
        int x=  1/ value;
        return true;
    }

}
