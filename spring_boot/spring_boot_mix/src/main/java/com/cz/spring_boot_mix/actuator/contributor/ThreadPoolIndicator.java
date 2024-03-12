package com.cz.spring_boot_mix.actuator.contributor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolIndicator implements HealthIndicator {

    private ThreadPoolExecutor executor;

    public ThreadPoolIndicator(ThreadPoolExecutor executor){
        this.executor = executor;
    }

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("queue_size", executor.getQueue().size());
        details.put("queue_remaining", executor.getQueue().remainingCapacity());
        if(executor.getQueue().remainingCapacity() > 0){
            // 每次查询health都执行一个任务, 第一次查询是UP， 后面因为任务积累，变为DOWN
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            return Health.up().withDetails(details).build();
        }
        return Health.down().withDetails(details).build();
    }
}
