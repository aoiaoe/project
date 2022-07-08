package com.cz.spring_boot_template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/7/7 : 14:41
 */
@RequestMapping(value = "/hello")
@RestController
public class HelloUndertowController {

    @GetMapping
    public String hello(){
        return "hello undertow";
    }

}
