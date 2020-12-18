package com.cz.spring_boot_mix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SpringBootMixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMixApplication.class, args);
    }

}
