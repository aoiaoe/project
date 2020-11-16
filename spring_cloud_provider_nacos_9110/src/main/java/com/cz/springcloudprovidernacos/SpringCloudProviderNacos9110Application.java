package com.cz.springcloudprovidernacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.cz")
public class SpringCloudProviderNacos9110Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderNacos9110Application.class, args);
    }

}
