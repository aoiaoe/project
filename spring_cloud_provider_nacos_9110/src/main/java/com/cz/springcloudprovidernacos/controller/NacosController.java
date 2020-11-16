package com.cz.springcloudprovidernacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alian
 * @date 2020/11/16 上午 11:53
 * @since JDK8
 */
@RefreshScope
@RequestMapping(value = "/nacos")
@RestController
public class NacosController {

    @Value("${key}")
    private String value;

    @GetMapping(value = "/key")
    public Object getKey(){
        return value;
    }
}
