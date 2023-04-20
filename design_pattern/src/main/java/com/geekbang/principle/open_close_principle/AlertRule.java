package com.geekbang.principle.open_close_principle;

/**
 * @author jzm
 * @date 2023/4/18 : 16:39
 */
public class AlertRule {

    private Integer maxTps;
    private Integer maxErrorCount;

    public AlertRule getMatchedRule(String api) {
        return null;
    }

    public Integer getMaxTps() {
        return maxTps;
    }

    public Integer getMaxErrorCount() {
        return maxErrorCount;
    }
}
