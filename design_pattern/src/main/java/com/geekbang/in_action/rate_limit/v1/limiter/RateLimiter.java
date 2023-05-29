package com.geekbang.in_action.rate_limit.v1.limiter;

import com.geekbang.in_action.rate_limit.v1.algos.RateLimitAlgo;
import com.geekbang.in_action.rate_limit.v1.algos.TimeWindowRateLimitAlgo;
import com.geekbang.in_action.rate_limit.v1.rules.ApiLimit;
import com.geekbang.in_action.rate_limit.v1.rules.RuleConfig;
import com.geekbang.in_action.rate_limit.v1.rules.rule_match.RateLimitRule;
import com.geekbang.in_action.rate_limit.v1.rules.rule_match.TrieRateLimitRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jzm
 * @date 2023/4/27 : 09:51
 */
public class RateLimiter {

    private Logger log = LoggerFactory.getLogger(RateLimiter.class);
    private ConcurrentHashMap<String, RateLimitAlgo> limits = new ConcurrentHashMap<>(64);
    // trie树匹配
    private RateLimitRule rule;
    private final String CONFIG_FILE = "/rate_limit.yml";

    public RateLimiter() {
        init();
    }

    /**
     * 将配置文件中的限流规则初始化到内存中
     */
    private void init() {
        RuleConfig ruleConfig;
        try (InputStream in = this.getClass().getResourceAsStream(CONFIG_FILE)) {
            Yaml yaml = new Yaml();
            ruleConfig = yaml.loadAs(in, RuleConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("load rule config error");
        }
        this.rule = new TrieRateLimitRule(ruleConfig);
    }

    public boolean limit(String appId,String url) throws Exception {
        ApiLimit limit = rule.getLimit(appId, url);
        if(limit == null){
            return true;
        }
        // 获取api对应在内存中的限流计数器（rateLimitCounter）
        String counterKey = appId + ":" + limit.getApi();
        RateLimitAlgo limitCounter = limits.get(counterKey);
        if(limitCounter == null){
            RateLimitAlgo newLimitCounter = new TimeWindowRateLimitAlgo(limit.getLimit(), limit.getUnit());
            limitCounter = limits.putIfAbsent(counterKey, newLimitCounter);
            if(limitCounter == null){
                limitCounter = newLimitCounter;
            }
        }
        return limitCounter.tryAcquire();
    }

}
