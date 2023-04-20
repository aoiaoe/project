package com.geekbang.principle.open_close_principle.good.handler;

import com.geekbang.principle.open_close_principle.AlertRule;
import com.geekbang.principle.open_close_principle.good.ApiStatusInfo;
import com.geekbang.principle.open_close_principle.good.notification.Notification;

public abstract class AlertHandler {

    protected AlertRule rule;
    protected Notification notification;

    public AlertHandler(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    public abstract void check(ApiStatusInfo statusInfo);
}