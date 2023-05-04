package com.geekbang.in_action.rate_limit.v1.rules;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author jzm
 * @date 2023/4/27 : 09:54
 */
@NoArgsConstructor
@Getter
@Setter
public class AppRuleConfig {

    private String appId;
    private List<ApiLimit> limits;

    public AppRuleConfig(String appId, List<ApiLimit> limits){
        this.appId = appId;
        this.limits = limits;
    }

}
