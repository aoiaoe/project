package com.cz.spring_boot_mix.importannotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2022/1/13 : 10:14
 */
@Slf4j
public class TestD {

    public TestD(){
        log.info("初始化TestD");
    }
    public void testD(){
        System.out.println("----> testD");
    }
}
