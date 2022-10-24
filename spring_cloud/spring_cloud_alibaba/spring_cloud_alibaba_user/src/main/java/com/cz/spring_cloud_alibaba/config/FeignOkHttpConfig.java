package com.cz.spring_cloud_alibaba.config;

import feign.Client;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.http.RealInterceptorChain;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 需要修改配置
 * feign:
 *   # 底层切换为OkHttp
 *   httpclient:
 *     enabled: false
 *   okhttp:
 *     enabled: true
 *
 * OpenFeign 使用 OkHttp 配置类
 * @author jzm
 * @date 2022/10/11 : 15:11
 */
@Slf4j
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .callTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) // 是否失败重连
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        RealInterceptorChain realChain = (RealInterceptorChain) chain;
                        Request request = realChain.request();
                        log.info("[okhttp 拦截器] 获取auth token:{}", request.header("auth"));
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    @Bean
    public Client feignClient(okhttp3.OkHttpClient client, LoadBalancerClient loadBalancerClient,
                              LoadBalancerProperties properties, LoadBalancerClientFactory loadBalancerClientFactory) {
        return new FeignBlockingLoadBalancerClient(new feign.okhttp.OkHttpClient(client), loadBalancerClient, properties, loadBalancerClientFactory);
    }
}
