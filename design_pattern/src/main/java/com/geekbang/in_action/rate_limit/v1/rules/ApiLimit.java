package com.geekbang.in_action.rate_limit.v1.rules;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jzm
 * @date 2023/4/27 : 09:54
 */
@NoArgsConstructor
@Setter
@Getter
public class ApiLimit {

    // 默认限流时间为1秒
    private static final int DEFAULT_TIME_UNIT = 1;
    private String api;
    private int limit;
    private int unit = DEFAULT_TIME_UNIT;

    public ApiLimit(String api, int limit){
        this.api = api;
        this.limit = limit;
    }

    public ApiLimit(String api, int limit, int unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }

}
