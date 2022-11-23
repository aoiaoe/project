package com.cz.blockingqueue;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 无容量同步队列
 * A线程放入一个元素，会阻塞到另一个线程来取
 * B线程获取一个元素，会阻塞到另一个线程保存元素
 *
 * @author alian
 * @date 2021/2/25 上午 9:43
 * @since JDK8
 */
public class SynchronousQueueDemo {

    private SynchronousQueue queue = new SynchronousQueue<String>();

    @Test
    public void test1() throws InterruptedException {
//        new Thread(() -> {
//            while (true) {
//                final Object take;
//                try {
//                    take = queue.take();
//                    System.out.println(Thread.currentThread().getName() + " 取出数据: " + take);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "C").start();
        new Thread(() -> {
            try {
                queue.put("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 放入 1");
        }, "A").start();
//        new Thread(() -> {
//            try {
//                queue.put("2");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + " 放入 2");
//        }, "B").start();


        System.out.println(queue.take());
        System.out.println(queue.take());

    }

}
