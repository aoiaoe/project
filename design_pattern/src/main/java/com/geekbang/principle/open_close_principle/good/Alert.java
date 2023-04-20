package com.geekbang.principle.open_close_principle.good;

import com.geekbang.principle.open_close_principle.good.handler.AlertHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzm
 * @date 2023/4/18 : 16:56
 */
public class Alert {

    private List<AlertHandler> handler = new ArrayList<>();

    public void check(ApiStatusInfo statusInfo){
        for (AlertHandler alertHandler : handler) {
            alertHandler.check(statusInfo);
        }
    }

    public void addHandler(AlertHandler handler){
        this.handler.add(handler);
    }
}
