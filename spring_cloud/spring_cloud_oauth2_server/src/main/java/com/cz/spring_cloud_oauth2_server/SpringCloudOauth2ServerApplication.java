package com.cz.spring_cloud_oauth2_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudOauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOauth2ServerApplication.class, args);
    }

}
