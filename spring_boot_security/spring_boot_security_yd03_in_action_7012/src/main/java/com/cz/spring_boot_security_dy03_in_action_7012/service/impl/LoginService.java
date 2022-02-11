package com.cz.spring_boot_security_dy03_in_action_7012.service.impl;

import com.cz.securitysdk.entity.LoginUser;
import com.cz.spring_boot_security_dy03_in_action_7012.config.TokenService;
import com.cz.spring_boot_security_dy03_in_action_7012.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String doLogin(LoginDTO loginDTO) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
        }catch (Exception e) {
            // <2.1> 发生异常，说明验证不通过，记录相应的登录失败日志
            if (e instanceof BadCredentialsException) {
                throw new RuntimeException("用户名或者密码不正确");
            } else {
                throw new RuntimeException(e.getMessage());
            }
        }
        // <3> 生成 Token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        return tokenService.createToken(loginUser);
    }
}
