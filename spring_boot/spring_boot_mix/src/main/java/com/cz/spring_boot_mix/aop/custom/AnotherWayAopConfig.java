package com.cz.spring_boot_mix.aop.custom;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzm
 * @date 2022/9/29 : 15:52
 */
@Configuration
public class AnotherWayAopConfig {

    @ConditionalOnClass(AopTest.class)
    @Bean
    public CustomAop customAop(){
        return new CustomAop(new CustomAdvisor());
    }
}
