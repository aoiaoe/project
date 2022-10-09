package com.cz.spring_cloud_alibaba.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class SentinelService {

    @SentinelResource("end")
    public String end(String msg){
        return msg;
    }
}
