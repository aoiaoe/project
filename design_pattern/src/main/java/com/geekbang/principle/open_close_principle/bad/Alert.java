package com.geekbang.principle.open_close_principle.bad;

import com.geekbang.principle.open_close_principle.AlertRule;
import com.geekbang.principle.open_close_principle.good.notification.Notification;
import com.geekbang.principle.open_close_principle.NotificationEmergencyLevel;

/**
 * 下面这段代码非常简单，业务逻辑主要集中在 check() 函数中。当接口的 TPS 超过某个预
 * 先设置的最大值时，以及当接口请求出错数大于某个最大允许值时，就会触发告警，通知接
 * 口的相关负责人或者团队。
 * 现在，如果我们需要添加一个功能，当每秒钟接口超时请求个数，超过某个预先设置的最大
 * 阈值时，我们也要触发告警发送通知。这个时候，我们该如何改动代码呢？主要的改动有两
 * 处：第一处是修改 check() 函数的入参，添加一个新的统计数据 timeoutCount，表示超时
 * 接口请求数；第二处是在 check() 函数中添加新的告警逻辑。
 * 如此往复，会导致check()函数的参数，方法体愈发臃肿，并且对于测试极其不友好
 */
public class Alert {
    private AlertRule rule;
    private Notification notification;

    public Alert(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    public void check(String api, long requestCount, long errorCount, long durationOfSeconds) {
        long tps = requestCount / durationOfSeconds;
        if (tps > rule.getMatchedRule(api).getMaxTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "...");
        }
        if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
            notification.notify(NotificationEmergencyLevel.SEVERE, "...");
        }
    }
}