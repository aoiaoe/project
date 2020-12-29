package com.cz.spring_boot_mix.circulardependency.setters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alian
 * @date 2020/12/25 下午 12:22
 * @since JDK8
 */
@Service
@Slf4j
public class Service6 {

    private Service5 service5;

    public Service6(){
        log.info("初始化service6、、、、");
    }

    @Autowired
    public void setService5(Service5 service5){
        this.service5 = service5;
    }
}
