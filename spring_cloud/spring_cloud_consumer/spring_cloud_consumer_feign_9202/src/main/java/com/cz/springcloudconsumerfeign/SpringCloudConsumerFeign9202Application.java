package com.cz.springcloudconsumerfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringCloudConsumerFeign9202Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerFeign9202Application.class, args);
    }

}
