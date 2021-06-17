package com.cz.lock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读读共享， 其他各情况互斥
 *
 * @author alian
 * @date 2021/2/1 下午 12:17
 * @since JDK8
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwlock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwlock.writeLock();

        List<String> list = new ArrayList<>();

        new Thread(() -> {
            readLock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "-" + Thread.currentThread().getName() + " got readLock");
                list.stream().forEach(System.out::println);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "-" + Thread.currentThread().getName() + " rel readLock");
        }, "A").start();

        new Thread(() -> {
            writeLock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "-" + Thread.currentThread().getName() + " got writeLock");
                list.add("asdsa");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "-" + Thread.currentThread().getName() + " rel writeLock");
        }, "C").start();

        new Thread(() -> {
            readLock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "-" + Thread.currentThread().getName() + " got readLock");
                list.stream().forEach(System.out::println);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "-" + Thread.currentThread().getName() + " rel readLock");
        }, "B").start();


    }
}
