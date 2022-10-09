package com.cz.spring_cloud_alibaba.controller.config;

import com.cz.spring_cloud_alibaba.config.MyNacosConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/9/23 : 14:37
 */
@RequestMapping(value = "/config")
@RestController
public class NacosConfigController {

    @Autowired
    private MyNacosConfigProperties nacosConfigProperties;

//    @Value("${nacos-config.test}")
//    private String test;

    @GetMapping(value = "test")
    public String display(){
        return nacosConfigProperties.toString();
    }


}
