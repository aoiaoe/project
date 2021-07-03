package com.cz.spring_boot_mix.circulardependency.autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alian
 * @date 2020/12/25 下午 12:20
 * @since JDK8
 */
@Slf4j
@Service
public class Service2 {

    @Autowired
    private Service1 service1;

    public Service2() {
        log.info("初始化 service2....");
    }
}
