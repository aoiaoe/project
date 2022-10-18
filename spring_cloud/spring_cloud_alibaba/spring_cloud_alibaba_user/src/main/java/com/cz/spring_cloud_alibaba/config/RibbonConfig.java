package com.cz.spring_cloud_alibaba.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author jzm
 * @date 2022/10/18 : 09:46
 */
@Slf4j
@Configuration
@AutoConfigureBefore(LoadBalancerAutoConfiguration.class)
public class RibbonConfig {

    @SentinelRestTemplate(
            blockHandler = "block",
            blockHandlerClass = RibbonSentinelBlockHandler.class,
            fallback = "fallback",
            fallbackClass = RibbonSentinelBlockHandler.class
    )
    @LoadBalanced // 使用注册中心进行负载均衡
    @Bean
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(ImmutableList.of(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                // 打印请求路径 请求参数的过滤器
                String path = request.getURI().getPath();
                log.info("[RestTemplate自定义过滤器] 请求路径:{}", path);
                return execution.execute(request, body);
            }
        }));

        return restTemplate;
    }
}