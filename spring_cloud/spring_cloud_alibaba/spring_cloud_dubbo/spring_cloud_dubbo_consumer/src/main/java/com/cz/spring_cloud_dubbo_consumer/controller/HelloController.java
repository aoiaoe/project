package com.cz.spring_cloud_dubbo_consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cz.spring_cloud_dubbo_intf.DubboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2023/5/29 : 16:31
 */
@RequestMapping(value = "/hello")
@RestController
public class HelloController {

    @Reference(version = "1.0.0")
    private DubboService dubboService;

    @GetMapping(value = "/world")
    public String world(String msg){
        return this.dubboService.sayHello(msg);
    }
}
