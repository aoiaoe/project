package com.cz.spring_boot_mix.importannotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2022/1/13 : 10:12
 */
@Slf4j
public class TestC {

    public TestC(){
        log.info("初始化TestC");
    }
    public void testC(){
        System.out.println("----> testC");
    }
}
