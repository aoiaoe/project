package com.cz.spring_boot_test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author jzm
 * @date 2022/3/30 : 10:56
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() throws UnknownHostException {
        return "hello from " + InetAddress.getLocalHost().getHostAddress();
    }
}
