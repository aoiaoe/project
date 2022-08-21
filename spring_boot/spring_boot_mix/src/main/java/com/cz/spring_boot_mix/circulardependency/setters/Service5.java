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
public class Service5 {

    private Service6 service6;

    public Service5() {
        log.info("初始化service5、、、、");
    }

    @Autowired
    public void setService6(Service6 service6) {
        this.service6 = service6;
    }
}
