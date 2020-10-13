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
public class RestTemplateConfig {

    // 这个bean只是一个单纯的http客户端,只能通过域名和ip对服务提供者进行访问
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    // 这个bean为结合注册中心实现负载均衡,以及通过服务提供者在注册中心注册的名字进行访问
    // 此注释开启ribbon的负载均衡,默认路由策略为RoundRobinRule
    @LoadBalanced
    @Bean(name = "ribbonRestTemplate")
    public RestTemplate ribbonRestTemplate(){
        return new RestTemplate();
    }
}
