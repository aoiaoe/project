package com.cz.springbootredis.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * 主要是注入 JetCacheProxyConfiguration 进行切面配置
 * 声明式注解的实现方式就是切面
 */
@EnableMethodCache(basePackages = "com.cz.springbootredis.service")
@Configuration
public class JetCacheConfig {
}
