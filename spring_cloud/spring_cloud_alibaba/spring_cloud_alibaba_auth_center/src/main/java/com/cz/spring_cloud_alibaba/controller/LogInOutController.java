package com.cz.spring_cloud_alibaba.controller;


import com.cz.spring_cloud_alibaba.domain.auth.JwtToken;
import com.cz.spring_cloud_alibaba.domain.auth.LoginDto;
import com.cz.spring_cloud_alibaba.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/10/9 : 16:34
 */
@RequestMapping(value = "/sign")
@RestController
public class LogInOutController {

    @Autowired
    private ILoginService loginService;

    @PostMapping(value = "/in")
    public JwtToken in(@RequestBody LoginDto param){
        return this.loginService.login(param);
    }
}
