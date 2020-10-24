package com.cz.ribbonrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对某一个微服务进行特殊化配置路由策略
 */
@Configuration
public class RibbonRuleCustomizer {

    @Bean
    public IRule rule(){
        return new RandomRule();
    }
}
