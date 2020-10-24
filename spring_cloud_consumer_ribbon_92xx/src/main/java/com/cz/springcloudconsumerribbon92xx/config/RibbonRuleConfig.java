package com.cz.springcloudconsumerribbon92xx.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alian
 * @date 2020/10/13 下午 12:27
 * @since JDK8
 */
@Configuration
public class RibbonRuleConfig {

    /**
     * 修改ribbon默认的轮训路由策略为随机策略
     * @return
     */
    @Bean
    public IRule randomRule(){
//        return new RandomRule();

        return new RoundRobinRule();
        // 使用自定义的路由策略
//        return new MyRibbonRoutRule();
    }
}
