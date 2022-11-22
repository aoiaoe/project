package com.cz.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读读共享， 其他各情况互斥
 *
 * t1、A获取读锁，持有读锁
 * t2、B尝试获取写锁， 阻塞
 * t3、C尝试获取读锁， 阻塞，因为CLA队列头为获取写锁的节点
 *
 * @author alian
 * @date 2021/2/1 下午 12:17
 * @since JDK8
 */
@Slf4j
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwlock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwlock.writeLock();

        StringBuffer sb = new StringBuffer();

        Thread a = new Thread(() -> {
            readLock.lock();
            try {
                log.info("获取到读锁");
                TimeUnit.HOURS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
            log.info("释放读锁");
        }, "A");
        a.start();

        Thread c = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("阻塞获取写锁");
            writeLock.lock();
            try {
                log.info("获取到写锁");
                sb.append("asdsa");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
            log.info("释放写锁");
        }, "C");
        c.start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("阻塞获取读锁");
            readLock.lock();
            readLock.lock();
            try {
               log.info("获取到读锁:{}", sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                readLock.unlock();
            }
            log.info("释放读锁");
        }, "B").start();


    }
}
