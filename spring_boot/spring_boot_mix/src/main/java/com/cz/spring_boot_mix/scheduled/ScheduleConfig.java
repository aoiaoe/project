package com.cz.spring_boot_mix.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置spring定时任务@Scheduled任务使用的线程池
 *
 * @author alian
 * @date 2020/12/18 上午 11:32
 * @since JDK8
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
