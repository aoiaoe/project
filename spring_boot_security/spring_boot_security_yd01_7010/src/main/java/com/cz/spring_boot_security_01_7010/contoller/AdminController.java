package com.cz.spring_boot_security_01_7010.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/hello")
@RestController
public class AdminController {

    @GetMapping(value = "/admin")
    public String hello(){
        return "hello admin";
    }
}
