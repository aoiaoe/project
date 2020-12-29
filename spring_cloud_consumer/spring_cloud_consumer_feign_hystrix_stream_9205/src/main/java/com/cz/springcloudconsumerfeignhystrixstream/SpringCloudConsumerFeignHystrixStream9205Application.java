package com.cz.springcloudconsumerfeignhystrixstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.cz.springcloudconsumerfeignhystrixstream.feign")
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConsumerFeignHystrixStream9205Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerFeignHystrixStream9205Application.class, args);
    }

}
