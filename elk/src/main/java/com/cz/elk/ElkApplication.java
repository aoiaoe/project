package com.cz.elk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cz.elk.mapper")
@SpringBootApplication
public class ElkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElkApplication.class, args);
    }

}
