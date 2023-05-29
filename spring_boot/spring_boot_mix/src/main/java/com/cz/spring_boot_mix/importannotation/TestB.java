package com.cz.spring_boot_mix.importannotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzm
 * @date 2022/1/13 : 10:11
 */
@Slf4j
@Configuration
public class TestB {

    public TestB(){
        log.info("初始化TestB");
    }

    public void testB(){
        System.out.println("-----> testB");
    }
}
