package com.cz.thread;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

    @Test
    public void test1(){
        final CountDownLatch cdl = new CountDownLatch(2);
        Random rd = new Random();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int slp = rd.nextInt(10);
                try {
                    System.out.println(System.currentTimeMillis() +" - " + Thread.currentThread().getName() + "开始等待");
                    cdl.await();
                    System.out.println(System.currentTimeMillis() +" - " + Thread.currentThread().getName() + "等待结束，继续等待");
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() +" - " + Thread.currentThread().getName() + " 完成任务");
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(System.currentTimeMillis() +" - " + "主线程倒2秒之后计数减1");
            cdl.countDown();

            TimeUnit.SECONDS.sleep(15);
            System.out.println(System.currentTimeMillis() +" - " + "主线程15秒倒计数减1");
            cdl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
