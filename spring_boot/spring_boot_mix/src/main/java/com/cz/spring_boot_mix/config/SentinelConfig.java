package com.cz.spring_boot_mix.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jzm
 * @date 2022/8/18 : 09:47
 */
@Slf4j
@Configuration
public class SentinelConfig {

    @PostConstruct
    public void init() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("/interface1");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);

        FlowRule flowRule2 = new FlowRule();
        flowRule2.setResource("/interface2");
        flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule2.setCount(2);


        List<FlowRule> list = new ArrayList<>();
        list.add(flowRule);
        list.add(flowRule2);
        FlowRuleManager.loadRules(list);


        //熔断规则：5s内调用接口出现异常次数超过3的时候, 进行熔断4秒
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("notReadRedis");
        rule.setCount(3);
        //熔断规则
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(4);
        rule.setStatIntervalMs(5000);
        degradeRules.add(rule);
        DegradeRuleManager.loadRules(degradeRules);

        EventObserverRegistry.getInstance().addStateChangeObserver("stateChangeLogger",
                (preStat, newState, degRule, value) -> {
            if(newState == CircuitBreaker.State.OPEN){
                log.info("断路器打开了");
            } else if(newState == CircuitBreaker.State.HALF_OPEN){
                log.info("断路器半开");
            } else {
                log.info("断路器关闭");
            }
                });

        log.info("----配置sentinel完成----");
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
