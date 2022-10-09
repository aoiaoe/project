package com.cz.spring_cloud_alibaba.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用JavaConfig方式进行流控配置:
 *      SentinelConfig.java
 */
@RequestMapping(value = "/test")
@RestController
public class SentinelTestController {

    // 代码配置了流控
    // SentinelConfig.java
    @GetMapping(value = "/sentinel1")
    public String sentinel1(){
        return "sentinel1";
    }

    @GetMapping(value = "/sentinel2")
    public String sentinel2(){
        return "sentinel2";
    }
}
