package com.cz.spring_boot_security_dy03_in_action_7012.config;

import com.cz.securitysdk.config.SdkSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jzm
 * @date 2022/2/12 : 12:48
 */
//@Configuration
public class MySecurityConfig extends SdkSecurityConfig {

    @Autowired
    private UserDetailsService sysUserServiceImpl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 设置用户加载类
                .userDetailsService(sysUserServiceImpl)
                // 设置BCrpyt密码编码器
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
