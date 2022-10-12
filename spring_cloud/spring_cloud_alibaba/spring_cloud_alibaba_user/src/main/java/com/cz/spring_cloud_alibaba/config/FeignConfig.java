package com.cz.spring_cloud_alibaba.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author jzm
 * @date 2022/10/11 : 14:35
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

    /**
     * OpenFeign开启重试
     * period =100 发起当前请求的时间间隔  单位毫秒
     * maxPeriod = 1000 发起当前请求的最大时间间隔， 但是毫秒
     * maxAttempts = 5 最大重试次数
     * @return
     */
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }

    @Bean
    public Request.Options options(){
        return new Request.Options(5000, TimeUnit.MILLISECONDS,
                5000, TimeUnit.MILLISECONDS,
                true
                );
    }
}
