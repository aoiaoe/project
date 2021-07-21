package com.cz.spring_boot_security_yd02_7011.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

// 开启PreAuthorize注解的使用
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                // 使用内存中的 InMemoryUserDetailsManager
                inMemoryAuthentication()
                // 不使用 PasswordEncoder 密码编码器
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                // 配置用户
                .withUser("admin").password("admin").roles("ADMIN")
                .and()
                .withUser("user").password("user").roles("USER");

        // Spring 内置了两种 UserDetailsManager 实现：
        //      InMemoryUserDetailsManager，和「2. 快速入门」是一样的。
        //      JdbcUserDetailsManager ，基于 JDBC的 JdbcUserDetailsManager

        // 实际项目中，我们更多采用调用 AuthenticationManagerBuilder#userDetailsService(userDetailsService) 方法，
        // 使用自定义实现的 UserDetailsService 实现类，更加灵活且自由的实现认证的用户信息的读取。
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 配置请求地址的权限
                .authorizeRequests()
                // 所有用户可访问
                .antMatchers("/hello/all").permitAll()
                // 需要 ADMIN 角色
                .antMatchers("/hello/admin").hasRole("ADMIN")
                // 需要 NORMAL 角色
                .antMatchers("/hello/user").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                // 除了以上的任何请求，访问的用户都需要经过认证
                .anyRequest().authenticated()
                .and()
                // 设置 Form 表单登录
                .formLogin()
//                .loginPage("") // 设置自定义登录页面
                .permitAll()
                .and()
                // 配置退出相关
                .logout()
//                .logoutUrl("") // 设置自定义退出页面
                .permitAll();
    }
}
