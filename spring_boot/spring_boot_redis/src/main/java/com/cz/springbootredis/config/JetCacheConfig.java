package com.cz.springbootredis.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

@EnableMethodCache(basePackages = "com.cz.springbootredis.service")
@Configuration
public class JetCacheConfig {
}
