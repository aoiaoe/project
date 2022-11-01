package com.cz.spring_cloud_alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cz.spring_cloud_alibaba.anntation.IgnoreCommonResponseBody;
import com.cz.spring_cloud_alibaba.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @SentinelResource(value = "testA")
    @IgnoreCommonResponseBody
    @GetMapping(value = "/testA")
    public String testA(){
        testService.testService("testA");
        return "testA";
    }

    @SentinelResource(value = "testB")
    @IgnoreCommonResponseBody
    @GetMapping(value = "/testB")
    public String testB(){
        testService.testService("testB");
        return "testB";
    }

}
