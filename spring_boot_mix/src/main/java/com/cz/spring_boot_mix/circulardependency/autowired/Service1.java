package com.cz.spring_boot_mix.circulardependency.autowired;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alian
 * @date 2020/12/25 下午 12:19
 * @since JDK8
 */
@Slf4j
@Service
public class Service1 {

    @Autowired
    private Service2 service2;

    public Service1() {
        log.info("初始化service1、、、、");
    }

    public void testDosth(){
        System.out.println("---service1---");
    }

    public void setService2(Service2 service2) {
        this.service2 = service2;
    }
}
