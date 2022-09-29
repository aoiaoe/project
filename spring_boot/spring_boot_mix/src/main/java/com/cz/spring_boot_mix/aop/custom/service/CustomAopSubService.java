package com.cz.spring_boot_mix.aop.custom.service;

import com.cz.spring_boot_mix.aop.custom.AopTest;
import org.springframework.stereotype.Service;

/**
 * @author jzm
 * @date 2022/9/29 : 15:54
 */
@Service
public class CustomAopSubService extends CustomAopService{

    @AopTest
    public String test(){
        return "test in sub class  with @AopTest";
    }

    @AopTest
    public String test1(){
        return "test 1 in sub class  without @AopTest";
    }
}
