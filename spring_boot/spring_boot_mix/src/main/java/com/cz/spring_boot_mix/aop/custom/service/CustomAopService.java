package com.cz.spring_boot_mix.aop.custom.service;

import com.cz.spring_boot_mix.aop.custom.AopTest;
import org.springframework.stereotype.Service;

/**
 * @author jzm
 * @date 2022/9/29 : 15:53
 */
@Service
public class CustomAopService {

    @AopTest
    public String test(){
        return "test in CustomAopService with @AopTest";
    }

    @AopTest
    public String test1(){
        return "test 1 in CustomAopService with @AopTest";
    }
}
