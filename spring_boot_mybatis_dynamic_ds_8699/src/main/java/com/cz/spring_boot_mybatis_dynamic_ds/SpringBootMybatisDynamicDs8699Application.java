package com.cz.spring_boot_mybatis_dynamic_ds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.cz.spring_boot_mybatis_dynamic_ds.mapper")
@SpringBootApplication
public class SpringBootMybatisDynamicDs8699Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisDynamicDs8699Application.class, args);
    }

}
