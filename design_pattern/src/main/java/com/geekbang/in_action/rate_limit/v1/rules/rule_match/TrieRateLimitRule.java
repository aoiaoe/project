package com.geekbang.in_action.rate_limit.v1.rules.rule_match;

import com.geekbang.in_action.rate_limit.v1.rules.ApiLimit;
import com.geekbang.in_action.rate_limit.v1.rules.AppRuleConfig;
import com.geekbang.in_action.rate_limit.v1.rules.RuleConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author jzm
 * @date 2023/4/27 : 10:21
 */
public class TrieRateLimitRule implements RateLimitRule {

    private Map<String, TrieLimitNode> trieMap;

    /**
     * 利用从配置文件中读取的配置，构建前缀匹配树
     * @param ruleConfig
     */
    public TrieRateLimitRule(RuleConfig ruleConfig){
       init(ruleConfig);
    }
    private void init(RuleConfig ruleConfig){
        trieMap = new HashMap<>();
        if(ruleConfig == null || ruleConfig.getConfigs() == null){
            return;
        }
        for (AppRuleConfig config : ruleConfig.getConfigs()) {
            TrieLimitNode root = new TrieLimitNode("/", new HashMap<>(), null);
            Optional.ofNullable(config.getLimits()).orElse(new ArrayList<>())
                    .forEach(e -> root.addLimit(e.getApi(), e));
            trieMap.put(config.getAppId(), root);
        }
    }

    @Override
    public ApiLimit getLimit(String appId, String api) {
        TrieLimitNode root = trieMap.get(appId);
        return root == null ? null : root.match(api);
    }

}
