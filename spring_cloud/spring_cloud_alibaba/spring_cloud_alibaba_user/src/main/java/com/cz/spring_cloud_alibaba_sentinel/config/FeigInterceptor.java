package com.cz.spring_cloud_alibaba_sentinel.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * feign拦截器，可以修改请求头和请求体
 */
@Configuration
public class FeigInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String name;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("client", name);
    }
}
