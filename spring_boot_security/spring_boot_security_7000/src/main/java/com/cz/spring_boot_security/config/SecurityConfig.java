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
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private UserDetailsService usersService;

    /**
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
     *
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
    }

    /**
     * 前后端分离项目使用
     *
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
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write("退出成功");
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                // session管理
                .sessionManagement()
                // session并发管理(在共享Session中会失效)
                // 配置账号最多允许多大session共存
                // 默认后登陆的用户会挤掉前面的账号
                .maximumSessions(1)
                // 是否开始session保护，既当到达上限时，不允许后续账号登录,不会挤掉之前登录的账号
                // 但是会出现bug, 前面的账号退出后,Security不能感知到,导致退出的账号不能再登录，只能等session自然过期之后才能再次登录
                // 故需要配置session的事件发布组件 HttpSessionEventPublisher
                .maxSessionsPreventsLogin(true);
    }

    /**
     * 由于配置最大session并存数之后,退出登录之后，Security不能感知到session已经退出，导致后续账号不能再次登录
     * 此组件会在session登录或者退出之后发布事件,Security会及时感知到
     *
     * @return
     */
    @Bean
    public HttpSessionEventPublisher eventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
