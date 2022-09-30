package com.cz.spring_cloud_alibaba_sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudAlibabaSentinelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaSentinelApplication.class, args);
	}

}