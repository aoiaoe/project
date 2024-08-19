package com.cz.spring_boot_redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author ZhaoBW
 * @version 1.0
 * @date 2021/1/25 14:24
 */
@Configuration
public class MyRedissonConfig {

    public static final String ADDR = "redis://tx-sh:17777";

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer()
                .setAddress(ADDR);
        return Redisson.create(config);
    }
    
}