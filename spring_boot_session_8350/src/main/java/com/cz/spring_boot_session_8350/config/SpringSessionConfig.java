package com.cz.spring_boot_session_8350.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

// 自动化配置 Spring Session 使用 Redis 作为数据源
@EnableRedisHttpSession
@Configuration
public class SpringSessionConfig {


    /**
     * 创建 {@link RedisOperationsSessionRepository} 使用的 RedisSerializer Bean 。
     *
     * 具体可以看看 {@link RedisHttpSessionConfiguration#setDefaultRedisSerializer(RedisSerializer)} 方法，
     * 它会引入名字为 "springSessionDefaultRedisSerializer" 的 Bean 。
     *
     * @return RedisSerializer Bean
     */
    @Bean(name = "springSessionDefaultRedisSerializer")
    public RedisSerializer springSessionDefaultRedisSerializer() {
        return RedisSerializer.json();
    }

    /**
     * 将sessionID存入Cokkie中,无需额外干预
     * @return
     */
//    @Bean
//    public CookieHttpSessionIdResolver sessionIdResolver() {
//        // 创建 CookieHttpSessionIdResolver 对象
//        CookieHttpSessionIdResolver sessionIdResolver = new CookieHttpSessionIdResolver();
//
//        // 创建 DefaultCookieSerializer 对象
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        sessionIdResolver.setCookieSerializer(cookieSerializer); // 设置到 sessionIdResolver 中
//        cookieSerializer.setCookieName("MY_SESSIONID");
//
//        return sessionIdResolver;
//    }

    /**
     * 将token放入Header中, 需要前端重写请求头
     * 登录成功之后, sessionId会以响应头的方式返回
     * 后续请求需要将sessionID以token为key的形式 放在header中进行请求验证
     * @return
     */
    @Bean
    public HeaderHttpSessionIdResolver sessionIdResolver() {
//        return HeaderHttpSessionIdResolver.xAuthToken();
//        return HeaderHttpSessionIdResolver.authenticationInfo();
        return new HeaderHttpSessionIdResolver("token");
    }

}
