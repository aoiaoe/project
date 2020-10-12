package com.cz.springcloudconsumerribbon92xx.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author alian
 * @date 2020/10/12 下午 6:07
 * @since JDK8
 */
@Configuration
public class RibbonConfig {

    // 移除下一行注释即可开启ribbon的负载均衡,默认路由策略为RoundRobinRule
//    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
