package com.cz.feignconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alian
 * @date 2020/10/30 下午 5:55
 * @since JDK8
 */
@Configuration
public class RoundRobinRuleConfig {

    @Bean
    public IRule roundRobin() {
        return new RoundRobinRule();
    }
}
