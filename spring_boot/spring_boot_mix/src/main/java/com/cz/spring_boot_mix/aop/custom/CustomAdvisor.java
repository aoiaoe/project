package com.cz.spring_boot_mix.aop.custom;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author jzm
 * @date 2022/9/29 : 15:50
 */
@Slf4j
public class CustomAdvisor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object res = invocation.proceed();
        log.info("切面测试: another way, 执行结果:{}", res);
        return res;
    }
}
