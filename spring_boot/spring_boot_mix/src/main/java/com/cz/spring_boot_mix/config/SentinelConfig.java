package com.cz.spring_boot_mix.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
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
    public void init(){
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("/test/sentinel1");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);

        FlowRule flowRule2 = new FlowRule();
        flowRule2.setResource("/test/sentinel2");
        flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule2.setCount(2);

        List<FlowRule> list = new ArrayList<>();
        list.add(flowRule);
        list.add(flowRule2);
        FlowRuleManager.loadRules(list);
        log.info("----配置sentinel完成----");
    }
}
