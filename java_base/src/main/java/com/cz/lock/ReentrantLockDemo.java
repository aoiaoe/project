package com.cz.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jzm
 * @date 2023/3/27 : 12:52
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("A获取了锁");
                TimeUnit.SECONDS.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.lock();
            }
        }, "A").start();
try {
    TimeUnit.SECONDS.sleep(1);
} catch (InterruptedException e) {
    e.printStackTrace();
}
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("B获取了锁");
            } catch (Exception e) {

            }
        }, "B").start();
    }
}
