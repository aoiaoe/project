package com.cz.spring_cloud_alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.cz.spring_cloud_alibaba.dao")
@EnableFeignClients(basePackages = "com.cz.spring_cloud_alibaba.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudAlibabaSentinelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaSentinelApplication.class, args);
	}

}
