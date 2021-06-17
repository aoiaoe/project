package com.cz.blockingqueue;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author alian
 * @date 2021/2/25 上午 9:43
 * @since JDK8
 */
public class SynchronousQueueDemo {

    private SynchronousQueue queue = new SynchronousQueue<String>();

    @Test
    public void test1() {
        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName());
                final Object take;
                try {
                    take = queue.take();
                    System.out.println(Thread.currentThread().getName() + " 取出数据: " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                queue.put("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 放入 1");
        }, "A").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                queue.put("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 放入 2");
        }, "B").start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
