package com.geekbang.principle.open_close_principle.good.notification;

import com.geekbang.principle.open_close_principle.NotificationEmergencyLevel;

/**
 * @author jzm
 * @date 2023/4/18 : 16:40
 */
public interface Notification {
    void notify(NotificationEmergencyLevel urgency, String s);
}
