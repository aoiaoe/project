package com.cz.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @title: SpinLockDemo
 * @description: 手写一个自旋锁、使用原则引用类的compareAndSet
 * @author：quLiangquan
 * @date 2019/8/16 15:01
 **/
public class SpinLockDemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + " come in");
        while (! atomicReference.compareAndSet(null, thread)) {

        }
    }
    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + " invoke myUnLock");
    }

    public static void main(String[] args) {

        //线程操作资源类
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.myLock();
            try {TimeUnit.SECONDS.sleep(5);} catch (Exception e) {e.printStackTrace();}
            spinLockDemo.myUnLock();
            System.out.println("out");
        }, "AAA").start();

        //保证A先启动、然后获得锁
        try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {e.printStackTrace();}

        new Thread(() -> {
            spinLockDemo.myLock();
            try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {e.printStackTrace();}
            spinLockDemo.myUnLock();
        }, "BBB").start();

    }
}
