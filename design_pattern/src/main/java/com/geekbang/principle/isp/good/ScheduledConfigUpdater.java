package com.geekbang.principle.isp.good;

import com.geekbang.principle.isp.good.service.Updater;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/4/19 : 10:35
 */
public class ScheduledConfigUpdater {

    private Updater updater;
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    public ScheduledConfigUpdater(Updater updater){
        this.updater = updater;
    }

    public void update(){
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            updater.update();
        }, 10, 10,TimeUnit.SECONDS);
    }
}
