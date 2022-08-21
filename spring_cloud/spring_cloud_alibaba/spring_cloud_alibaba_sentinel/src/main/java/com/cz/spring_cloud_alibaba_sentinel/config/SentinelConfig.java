package com.cz.spring_cloud_alibaba_sentinel.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
        flowRule.setResource("/test/sentinel1"); // 配置需要限流的资源
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS); // 配置流控级别 0:线程数  1:QPS
        flowRule.setCount(1); // 设置流控数量

        FlowRule flowRule2 = new FlowRule();
        flowRule2.setResource("/test/sentinel2");
        flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule2.setCount(1);

        List<FlowRule> list = new ArrayList<>();
        list.add(flowRule);
        list.add(flowRule2);
        FlowRuleManager.loadRules(list);
        log.info("----配置sentinel完成----");

        // 测试，启动之后，运行时修改流控规则
        // 不过FlowRuleManager.loadRules(list);中的参数list必须为新的list, 否则不会更新
        // 并且list中须为全部的流控规则，不能只有修改后的规则，否则只会有最新的规则
        // 例如，下面的延迟任务，只增加了一个流控规则，修改后，使第二个流控规则失效了
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                flowRule.setCount(100);
                List<FlowRule> list = new ArrayList<>();
                list.add(flowRule);
                FlowRuleManager.loadRules(list);
                log.info("更改完成");
            }
        }, 5000L);
    }
}
