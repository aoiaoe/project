package com.cz.feignconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alian
 * @date 2020/10/30 下午 5:54
 * @since JDK8
 */
@Configuration
public class RandomFeignRuleConfig {

    @Bean
    public IRule random() {
        return new RandomRule();
    }
}
