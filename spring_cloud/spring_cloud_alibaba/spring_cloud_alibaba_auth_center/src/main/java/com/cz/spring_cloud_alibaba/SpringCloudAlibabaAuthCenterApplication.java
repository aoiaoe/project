package com.cz.spring_cloud_alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.cz.spring_cloud_alibaba.mapper")
@SpringBootApplication
public class SpringCloudAlibabaAuthCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaAuthCenterApplication.class, args);
	}

}
