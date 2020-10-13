package com.cz.springcloudconsumerribbon92xx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConsumerRibbon92xxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerRibbon92xxApplication.class, args);
    }

}
