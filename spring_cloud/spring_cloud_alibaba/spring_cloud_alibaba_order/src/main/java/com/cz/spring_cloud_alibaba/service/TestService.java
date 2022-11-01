package com.cz.spring_cloud_alibaba.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    @SentinelResource(value = "chainDestination")
    public void testService(String entrance){
      log.info("entrance:{}", entrance);
    }
}
