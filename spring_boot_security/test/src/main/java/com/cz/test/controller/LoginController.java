package com.cz.test.controller;

import com.cz.test.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/action")
@RestController
public class LoginController {

    @PostMapping(value = "/test")
    public String test(){
        return "/action/test";
    }
}
