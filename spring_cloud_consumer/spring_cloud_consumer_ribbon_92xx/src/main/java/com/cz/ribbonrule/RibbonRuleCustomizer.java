package com.cz.ribbonrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对Ribbon客户端进行配置
 * 如果想对某一个微服务进行特殊化配置路由策略，则此类不能存在于@ComponentScan包及子包下
 * 否则将会适用于所有Ribbon客户端,起不到针对某一服务Ribbon客户端定制化配置的需求
 */
@Configuration
public class RibbonRuleCustomizer {

    @Bean
    public IRule rule(){
        return new RandomRule();
    }
}
