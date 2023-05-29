package com.geekbang.in_action.rate_limit.v1.rules;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jzm
 * @date 2023/4/27 : 09:53
 */
@Setter
public class RuleConfig {

    private List<AppRuleConfig> configs;

    public List<AppRuleConfig> getConfigs(){
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs){
        this.configs = configs;
    }
}
