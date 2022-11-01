package com.cz.spring_cloud_alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan(basePackages = "com.cz.spring_cloud_alibaba.dao")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringCloudAlibabaProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaProviderApplication.class, args);
	}

}
