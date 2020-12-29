package com.cz.spring_boot_mix.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author alian
 * @date 2020/12/29 下午 12:25
 * @since JDK8
 */
@Slf4j
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.cz.spring_boot_mix.aop..*.*(..))")
    public void pointCut(){}

    @Pointcut("@annotation(com.cz.spring_boot_mix.aop.MyAopAnnotation)")
    public void annoPointCut(){}

    @Before("pointCut()")
    public void before(){
        log.info("before logic...");
    }

    @After("pointCut()")
    public void after(){
        log.info("after  logic...");
    }

    @Around("annoPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("before logic in around.....");
        joinPoint.proceed();
        log.info("after logic in around.....");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        log.info("after return  logic...");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        log.info("after throwing logic...");
    }


}
