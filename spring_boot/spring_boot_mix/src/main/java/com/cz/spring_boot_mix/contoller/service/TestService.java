package com.cz.spring_boot_mix.contoller.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cz.spring_boot_mix.contoller.bean.DemoBean;
import com.cz.spring_boot_mix.contoller.bean.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;

    public MyBean test(MyBean myBean){
        try{
            int x = 1/ 0;
        } catch (Exception e){

        }
        return myBean;
    }

    public int test(int c){
        return c;
    }

    @SentinelResource(value = "reqRemoteFallback", fallback = "fallbackFB")
    public String fallback() {
        String res = restTemplate.getForObject("http://192.168.18.203:8888/test",String.class);
        return res;
    }

    public String fallbackFB() {
        return "降级";
    }

    public List<DemoBean> demoBeanList(){
        Random random = new Random();
        final Long start = Long.valueOf(random.nextInt(100000));
        return LongStream.range(1, 5).mapToObj(i -> {
            String sex = i % 2 == 0? "男" : "女";
            return new DemoBean(start + i, "name" + i, sex);
        }).collect(Collectors.toList());
    }
}
