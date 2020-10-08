package com.cz.single_db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cz.single_db.mapper")
@SpringBootApplication
public class SingleDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleDbApplication.class, args);
    }

}
