package com.cz.springcloudhystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class SpringCloudHystrixDashboard9090Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixDashboard9090Application.class, args);
    }

}
