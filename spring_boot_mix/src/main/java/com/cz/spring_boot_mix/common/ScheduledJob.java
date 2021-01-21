package com.cz.spring_boot_mix.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 启用springboot定时任务,需要在启动类上添加@EnableScheduling注解
 * 默认情况下,所有@Scheduled注解的定时任务都用的同一个线程,所有如果在同一时刻需要执行的任务过多,则造成所有任务随机串行执行
 *
 * 解决方案：使用自定义线程池运行任务，可以使定时任务并行进行
 *         方式有两种:
 *              1、开启同步，在@Scheduled方法上加上@Async注解,并且@Async(value = "线程池名")中要配置使用的线程池名
 *              2、实现SchedulingConfigurer接口,并配置定时任务使用的线程池
 *              注意：两种方式同时启动时，只有方式1会生效
 * @author alian
 * @date 2020/12/18 上午 10:49
 * @since JDK8
 */
@Slf4j
@Service
public class ScheduledJob {

    private static final String CRON = "0/10 * * * * ?";

    @Async("threadPoolExecutor")
    @Scheduled(cron = CRON)
    public void job1(){
        log.info("job1 ----> 线程名:{}", Thread.currentThread().getName());
    }
    @Async("threadPoolExecutor")
    @Scheduled(cron = CRON)
    public void job2(){
        log.info("job2 ----> 线程名:{}", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
    }
    @Async("threadPoolExecutor")
    @Scheduled(cron = CRON)
    public void job3(){
        log.info("job3 ----> 线程名:{}", Thread.currentThread().getName());
    }
}