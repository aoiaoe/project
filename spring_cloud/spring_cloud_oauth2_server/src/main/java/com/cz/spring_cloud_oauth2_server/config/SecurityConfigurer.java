package com.cz.spring_cloud_oauth2_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * 该配置类，主要处理用户名和密码的校验登
 */
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 注册一个认证服务器到容器
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 处理用户名和密码验证
     * 1> 客户端传递的用户名和密码到认证服务器
     * 2> 一般来说，用户名和密码存储在数据库中
     * 3> 根据用户表中数据，校验合法性
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在这个方法中可以去关联数据库
        // 也可以使用内存中的数据(快速搭建测试)

        // 实例化一个内存中用户对象，相当于数据库表中的一个记录
//        UserDetails user = new User("jzm", "jzm", new ArrayList<>());
//        auth.inMemoryAuthentication()
//                .withUser(user)
//                .passwordEncoder(this.passwordEncoder());

        // 改造数据库获取用户信息
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    /**
     * 密码编码器，不加密的编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}