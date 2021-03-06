package com.cz.springcloudprovideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.cz.springcloudprovideruser.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudProviderUserSleuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderUserSleuthApplication.class, args);
    }

}
