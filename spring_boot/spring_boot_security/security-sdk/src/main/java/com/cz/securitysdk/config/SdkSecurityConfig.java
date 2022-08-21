package com.cz.securitysdk.config;

import com.alibaba.fastjson.JSON;
import com.cz.securitysdk.filter.JwtFilter;
import com.cz.securitysdk.vo.AjaxResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;

/**
 * 开启方法权限校验
 * prePostEnabled = true必须开启,否则报下面错
 * In the composition of all global method configuration, no annotation support was actually activated
 */
@Configuration
public class SdkSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private TokenProperties properties;

    @Autowired
    private AccessDeniedHandler ajaxAccessDeniedHandler;

    @Autowired(required = false)
    private UserDetailsService sysUserServiceImpl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if(sysUserServiceImpl != null) {
            auth
                    // 设置用户加载类
                    .userDetailsService(sysUserServiceImpl)
                    // 设置BCrpyt密码编码器
                    .passwordEncoder(new BCryptPasswordEncoder());
        }
    }

    /**
     * configure(WebSecurity)用于影响全局安全性(配置资源，设置调试模式，通过实现自定义防火墙定义拒绝请求)的配置设置。
     *
     * 一般用于配置全局的某些通用事物，例如静态资源等
     *
     * 这里需要配置成和JwtFilter中忽略同样的url,  否则验证失败,因为如果jwtFilter不校验,则会生成匿名用户对象在UsernamePasswordAuthenticationFilter进行校验
     * 所以 jwtFilter忽略过滤的url集合 需要是此处被忽略url的子集
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**", "/templates/**");
        if(!CollectionUtils.isEmpty(properties.getIgnoreUrls())) {
            properties.getIgnoreUrls().forEach(url ->  web.ignoring().antMatchers(url));
        }
    }

    /**
     * configure(HttpSecurity)允许基于选择匹配在资源级配置基于网络的安全性。
     *
     * 也就是对角色的权限——所能访问的路径做出限制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // csrf禁用，因为不使用session
                .csrf().disable()
                // 基于JWT,不需要保存session状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 认证失败的处理端点
                .exceptionHandling().authenticationEntryPoint(errorAuthenticationEntryPoint())
                // 访问权限错误拒绝处理器
                .accessDeniedHandler(ajaxAccessDeniedHandler)
                .and()
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                // 或者使用上面的public void configure(WebSecurity web)方法配置
                .antMatchers("/action/login").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .formLogin().disable()
                .logout().disable();

                // 肥肠重要
                // 一定要将自定义jwt过滤器配置在用户名密码解析过滤器前面
                // JWT只是用户信息的载体,最终用户校验是在UsernamePasswordAuthenticationFilter
                // 所以JwtFilter作用是将JWT承载的信息组装成UsernamePasswordAuthenticationFilter所需要的UsernamePasswordAuthenticationToken对象
                // 然后将UsernamePasswordAuthenticationToken对象放入上下文中,以便UsernamePasswordAuthenticationFilter使用
                // 如果JwtFilter不产生用户对象,则框架会生成匿名对象供UsernamePasswordAuthenticationFilter校验,肯定会报未登录错误
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public AuthenticationEntryPoint errorAuthenticationEntryPoint() {
        return (request, response, e) -> {
            AjaxResponseBody responseBody = new AjaxResponseBody();

            responseBody.setStatus("000");
            responseBody.setMsg("认证失败");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(responseBody));
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, e) -> {
            AjaxResponseBody responseBody = new AjaxResponseBody();

            responseBody.setStatus("400");
            responseBody.setMsg("Login Failure!");

            response.getWriter().write(JSON.toJSONString(responseBody));
        };
    }


    /**
     * 注入AuthenticationManager, 否则业务代码中注入会失败
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
        System.out.println(new BCryptPasswordEncoder().encode("user"));
    }
}
