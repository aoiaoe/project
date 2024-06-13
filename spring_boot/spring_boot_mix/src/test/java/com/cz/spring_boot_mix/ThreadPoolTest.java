package com.cz.spring_boot_mix;

import org.junit.Test;

import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String argr[]) throws Exception{
//        test1();

        test2();

        TimeUnit.HOURS.sleep(1);
    }

    public static void test2(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        executor.execute(() -> System.out.println("thread pool task done"));
    }

    public static void test1() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));

        executor.execute(() -> {
            while (true){
                System.out.println("--------------------------");
                System.out.println("活跃线程数:" + executor.getActiveCount());
                System.out.println("池中线程数:" + executor.getPoolSize());
                System.out.println("队列任务数:" + executor.getQueue().size());
                System.out.println("--------------------------");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        TimeUnit.SECONDS.sleep(2);
        executor.execute(new TimeTask(1));
        executor.execute(new TimeTask(2));
        TimeUnit.SECONDS.sleep(2);
        executor.setCorePoolSize(2);
        TimeUnit.SECONDS.sleep(4);
//        executor.setCorePoolSize(1);
    }
}

class TimeTask extends Thread{

    private int x ;
    public TimeTask(int x){
        this.x = x;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(x + "任务完成" + System.currentTimeMillis());
    }
}
