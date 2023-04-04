package com.cz.spring_boot_mix.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author jzm
 * @date 2023/3/29 : 17:18
 */
@Slf4j
//@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("开始CommandLineRunner中的死循环");
        while (true){

        }
    }
}
