package com.cz.spring_boot_security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private UserDetailsService usersService;

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        使用内存中的用户
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("admin").roles("admin")
//                .and()
//                .withUser("user").password("user").roles("user");

        // 搭配数据库
        auth.userDetailsService(usersService);
    }

    /**
     * 前后端不分离项目使用
     * @param http
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                // loginPage会将登录页和登录接口同时修改
//                // 亦可以使用loginProcessingUrl单独配置登录接口
//                .loginPage("/login.html")
//                // 指定表单登录用户名参数名
//                .usernameParameter("name")
//                // 指定表单登录密码参数名
//                .passwordParameter("pw")
//                // 指定登陆成功回调
//                .successForwardUrl("/success")
//                // 指定登录失败回调
//                .failureForwardUrl("/fail")
//                .permitAll()
//                .and()
//                // 配置登出相关
//                .logout()
//                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))
//                .logoutSuccessUrl("/index")
//                .deleteCookies()
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .permitAll()
//                .and()
//                .csrf().disable();
//    }

    /**
     * 角色继承, 代表admin自动获得user的权限
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
    }

    /**
     * 前后端分离项目使用
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 拥有admin权限才能访问/admin开头的路径
                // 拥有user权限才能访问/user开头的路径
                // 如果需要实现admin可以获得user的权限,则需要设置角色继承
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                // 登录成功之后  响应一个json
                .successHandler((req, resp, auth) -> {
                    Object principal = auth.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(e.getMessage());
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
                // 验证失败的处理端点
                .authenticationEntryPoint((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    resp.setStatus(HttpStatus.FORBIDDEN.value());
                    PrintWriter out = resp.getWriter();
                    out.write("未登录，请先登录");
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, auth) -> {
                    Object principal = auth.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("退出成功");
                    out.flush();
                    out.close();
                })
                .permitAll();
    }
}
