package com.cz.spring_boot_security_dy03_in_action_7012.controller;

import com.cz.spring_boot_security_dy03_in_action_7012.dto.LoginDTO;
import com.cz.spring_boot_security_dy03_in_action_7012.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/action")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return this.loginService.doLogin(loginDTO);
    }

    @PostMapping(value = "/test")
    public String test(){
        return "/action/test";
    }
}
