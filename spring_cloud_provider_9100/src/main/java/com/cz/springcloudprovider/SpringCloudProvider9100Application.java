package com.cz.springcloudprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.cz"})
public class SpringCloudProvider9100Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProvider9100Application.class, args);
    }

}
