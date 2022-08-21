package com.cz.springbootmybatissourcecode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.cz.springbootmybatissourcecode.mapper")
@SpringBootApplication
public class SpringBootMybatisSourceCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisSourceCodeApplication.class, args);
    }

}
