package com.cz.spring_boot_dubbo_provider.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.cz.spring_cloud_dubbo_intf.DubboService;

import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/5/29 : 16:30
 */
@Service(version = "1.0.0")
public class MyDubboService implements DubboService {

    @Override
    public String sayHello(String msg) throws Exception {
//        TimeUnit.SECONDS.sleep(2);
        return "Hello world from provider, msg from consumer:" + msg + " time: " +  System.currentTimeMillis();
    }

}
