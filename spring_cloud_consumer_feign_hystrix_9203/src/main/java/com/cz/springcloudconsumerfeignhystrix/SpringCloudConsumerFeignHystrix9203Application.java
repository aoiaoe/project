package com.cz.springcloudconsumerfeignhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableHystrix
@SpringBootApplication
public class SpringCloudConsumerFeignHystrix9203Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerFeignHystrix9203Application.class, args);
    }

}
