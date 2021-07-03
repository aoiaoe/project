package com.cz.thread.prod_cons;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模式2
 * 可重入锁
 *
 * @author alian
 * @date 2020/2/28 上午 9:50
 * @since JDK8
 */
public class LockProdConsDemo {

    public static void main(String[] args) {
        MyResource resource = new MyResource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.produce();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.produce();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.consume();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.consume();
            }
        }, "D").start();
    }

}

class MyResource {

    private int x = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce() {
        lock.lock();
        try {
            while (x >= 2) {
                condition.await();
            }
            x = x + 1;
            System.out.println(Thread.currentThread().getName() + "生产 1 now : " + x);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();
        try {
            while (x <= 0) {
                condition.await();
            }
            x = x - 1;
            System.out.println(Thread.currentThread().getName() + "消费 1 now : " + x);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
