package com.cz.spring_cloud_alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.cz.spring_cloud_alibaba.dao")
@SpringBootApplication
public class SpringCloudAlibabaProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaProviderApplication.class, args);
	}

}
