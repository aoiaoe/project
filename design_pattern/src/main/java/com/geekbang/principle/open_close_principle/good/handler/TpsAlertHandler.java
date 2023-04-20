package com.geekbang.principle.open_close_principle.good.handler;

import com.geekbang.principle.open_close_principle.AlertRule;
import com.geekbang.principle.open_close_principle.good.ApiStatusInfo;
import com.geekbang.principle.open_close_principle.good.notification.Notification;
import com.geekbang.principle.open_close_principle.NotificationEmergencyLevel;

/**
 * @author jzm
 * @date 2023/4/18 : 16:51
 */
public class TpsAlertHandler extends AlertHandler {

    public TpsAlertHandler(AlertRule rule, Notification notification) {
        super(rule, notification);
    }

    @Override
    public void check(ApiStatusInfo statusInfo) {
        if (statusInfo.getTps() > rule.getMatchedRule(statusInfo.getApi()).getMaxTps()) {
            notification.notify(NotificationEmergencyLevel.SEVERE, "...");
        }
    }
}
