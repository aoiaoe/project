package com.cz.spring_boot_security_dy03_in_action_7012;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@MapperScan(basePackages = "com.cz.spring_boot_security_dy03_in_action_7012.mapper")
@SpringBootApplication
public class SpringBootSecurityDy03InAction7012Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDy03InAction7012Application.class, args);
    }

}
