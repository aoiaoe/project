package com.geekbang.in_action.rate_limit.v1.rules.rule_match;

import com.geekbang.in_action.rate_limit.v1.rules.ApiLimit;

public interface RateLimitRule {
    ApiLimit getLimit(String appId, String api);
}