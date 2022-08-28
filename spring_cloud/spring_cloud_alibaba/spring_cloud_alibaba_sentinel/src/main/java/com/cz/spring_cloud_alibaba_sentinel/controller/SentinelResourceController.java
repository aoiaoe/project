package com.cz.spring_cloud_alibaba_sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cz.spring_cloud_alibaba_sentinel.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/resource")
@RestController
public class SentinelResourceController {

    @Autowired
    private SentinelService sentinelService;

    @SentinelResource(value = "entrance1")
    @GetMapping(value = "/entrance1")
    public String entrance1(){
        return sentinelService.end("entrance1");
    }

    @SentinelResource(value = "entrance2")
    @GetMapping(value = "/entrance2")
    public String entrance2(){
        return sentinelService.end("entrance2");
    }
}
