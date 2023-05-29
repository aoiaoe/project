package com.cz.spring_boot_zookeeper.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZkClientLockTest {

    private static Integer count = 50;
    private static CountDownLatch cdl = new CountDownLatch(count);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 模拟50个并发同时去创建订单号。
                    OrderService orderService = new OrderService();
                    try {
                        //线程运行起来时先等待。
                        cdl.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    orderService.createOrderNum();
                }
            }).start();
            cdl.countDown();
        }
        TimeUnit.MINUTES.sleep(3);
    }
}
