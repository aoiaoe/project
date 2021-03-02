package com.cz.spring_boot_mix.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author alian
 * @date 2020/12/18 上午 11:08
 * @since JDK8
 */
@Configuration
public class ThreadExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutor(10, 10,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("My_Customizer_Thread_" + atomicInteger.incrementAndGet());
                return t;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService(){
        return new ScheduledThreadPoolExecutor(10, new ThreadFactory() {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("My_Customizer_Scheduled_Thread_" + atomicInteger.incrementAndGet());
                return t;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
