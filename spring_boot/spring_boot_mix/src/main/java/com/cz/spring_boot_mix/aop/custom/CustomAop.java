package com.cz.spring_boot_mix.aop.custom;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * 创建切面的另一种方式
 * @author jzm
 * @date 2022/9/29 : 15:46
 */
public class CustomAop extends AbstractPointcutAdvisor {

    private Pointcut pointcut;

    private Advice advice;

    public CustomAop(Advice advice){
        this.advice = advice;
        this.pointcut = buildPoint();
    }

    public Pointcut buildPoint(){
        Pointcut cpc = new AnnotationMatchingPointcut(AopTest.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(AopTest.class);
        return cpc;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
