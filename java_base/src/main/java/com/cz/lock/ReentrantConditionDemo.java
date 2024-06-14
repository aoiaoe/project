package com.cz.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition条件等待的前提是要先获取锁
 * @author jzm
 * @date 2023/3/23 : 17:57
 */
public class ReentrantConditionDemo {

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        new ReentrantConditionDemo().test();
    }

    public void test()  throws InterruptedException {

        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
            System.out.println("A");
        }, "A").start();

//        TimeUnit.SECONDS.sleep(1);

        Thread b = new Thread(() -> {
            lock.lock();
            try {
                // 会报错，
                // 因为等待的前提是，要获取锁
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            System.out.println("B");
        }, "B");
        b.start();

        TimeUnit.SECONDS.sleep(1);
//        TimeUnit.SECONDS.sleep(1000);
        lock.lock();
        try {
            condition.signalAll();
        }finally {
            lock.unlock();
        }

        Thread.currentThread().join();
    }
}
