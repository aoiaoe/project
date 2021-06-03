package com.cz.spring_boot_mix.contoller;

import com.cz.spring_boot_mix.aop.AopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/aop")
@RestController
public class AopController {

    @Autowired
    private AopServiceImpl aopService;

    @GetMapping(value = "/1")
    public boolean doSth(){
        this.aopService.doSth();
        return true;
    }
    @GetMapping(value = "/2")
    public boolean doSthWithException(){
        this.aopService.doSthWithException();
        return true;
    }
    @GetMapping(value = "/3")
    public boolean doSthWithOutAroundAspectWithOutException(){
        this.aopService.doSthWithOutAroundAspectWithOutException();
        return true;
    }
    @GetMapping(value = "/4")
    public boolean doSthWithOutAroundAspectWithException(){
        this.aopService.doSthWithOutAroundAspectWithException();
        return true;
    }
}
