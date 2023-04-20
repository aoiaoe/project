package com.geekbang.principle.open_close_principle.good;

/**
 * @author jzm
 * @date 2023/4/18 : 16:49
 */
public class ApiStatusInfo {

    private String api;
    private Integer tps;
    private Integer errorCount;

    public Integer getTps() {
        return tps;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public String getApi() {
        return api;
    }
}
