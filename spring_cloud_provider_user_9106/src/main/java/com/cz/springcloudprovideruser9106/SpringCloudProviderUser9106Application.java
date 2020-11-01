package com.cz.springcloudprovideruser9106;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudProviderUser9106Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderUser9106Application.class, args);
    }

}
