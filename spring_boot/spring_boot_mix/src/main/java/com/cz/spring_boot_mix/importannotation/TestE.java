package com.cz.spring_boot_mix.importannotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jzm
 * @date 2023/5/5 : 11:12
 */
@Slf4j
public class TestE {

    public TestE(){
        log.info("初始化testE");
    }

    public void testE(){
        log.info("testE");
    }
}
