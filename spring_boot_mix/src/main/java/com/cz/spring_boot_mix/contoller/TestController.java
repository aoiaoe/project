package com.cz.spring_boot_mix.contoller;

import com.cz.spring_boot_mix.aop.AopServiceImpl;
import com.cz.spring_boot_mix.event.MyEvent;
import com.cz.spring_boot_mix.event.MyEventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/test")
@RestController
public class TestController {

    @Autowired
    private ApplicationEventMulticaster applicationEventMulticaster;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private Environment environment;

    @GetMapping(value = "/initBinderStringTrimmerEditor")
    public String getStringValue(String value){
        Long.parseLong(value);
        publisher.publishEvent(new MyEvent(new MyEventSource("cz")));
        return value;
    }

    @GetMapping(value = "/env")
    public String getEnvironment(String name){
        return environment.getProperty(name);
    }

    // SentinelConfig中配置了限流
    @GetMapping(value = "/sentinel1")
    public String testSentinel1(){
        return "sentinel1";
    }

    @GetMapping(value = "/sentinel2")
    public String sentinel2(){
        return "sentinel2";
    }


}
