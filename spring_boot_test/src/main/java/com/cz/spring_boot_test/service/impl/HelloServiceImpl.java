package com.cz.spring_boot_test.service.impl;

import com.cz.spring_boot_test.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author jzm
 * @date 2022/8/3 : 14:05
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return "hello " + msg;
    }
}
