package com.cz.thread;

import java.util.concurrent.TimeUnit;

/**
 * 排查java程序错误
 * <p>
 * 使用jps查看java进程号
 * 获得进程号之后使用jstack 进程号 分析错误产生原因
 *
 * @author alian
 * @date 2020/3/5 上午 10:00
 * @since JDK8
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        DeadLock loc1 = new DeadLock(lockA, lockB);
        DeadLock loc2 = new DeadLock(lockB, lockA);

        new Thread(() -> {
            try {
                loc1.lockAWantB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
                loc2.lockAWantB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

}

class DeadLock {

    private String lockA;
    private String lockB;

    public DeadLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    public void lockAWantB() throws InterruptedException {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " lock : " + lockA + "\t尝试获取" + lockB);
            TimeUnit.SECONDS.sleep(1);
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "获取" + lockB);
            }
        }
    }
}
