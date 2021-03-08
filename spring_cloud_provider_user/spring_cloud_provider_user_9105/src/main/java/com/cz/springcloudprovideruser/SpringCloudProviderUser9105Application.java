package com.cz.springcloudprovideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.cz")
public class SpringCloudProviderUser9105Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderUser9105Application.class, args);
    }

}
