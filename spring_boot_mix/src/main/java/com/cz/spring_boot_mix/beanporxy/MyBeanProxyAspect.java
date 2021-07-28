package com.cz.spring_boot_mix.beanporxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class MyBeanProxyAspect {

    @Pointcut("execution(* com.cz.spring_boot_mix.beanporxy.Child.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(){
        log.info("{}:执行之前");
    }

}
