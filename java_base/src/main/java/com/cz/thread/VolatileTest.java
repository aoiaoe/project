package com.cz.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jzm
 * @date 2023/3/17 : 16:37
 */
@Slf4j
public class VolatileTest {

    private boolean flag = true;

    public static void main(String[] args) {
//       new VolatileTest().parkTest();
       new VolatileTest().commandResortTest();

    }

    public void parkTest(){
        VolatileTest ins = new VolatileTest();


        Thread A = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("睡眠结束，A开始park");
            LockSupport.park(this);
            log.info("A");
        }, "A");

        A.start();
//        LockSupport.unpark(A);
//        log.info("main线程unpark A");
    }

    public void commandResortTest(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                Singleton ins = Singleton.getInstance();
                if(ins.name == null) {
                    System.out.println(Thread.currentThread().getName() + " -> " + ins.name);
                }
            }, "thread" + i).start();
        }
    }

    private static class Singleton{

        public String name;

        private static Singleton instance;
        private Singleton(){
            this.name = "CZ";
        }
        public static Singleton getInstance(){
            if(instance == null){
                synchronized (Singleton.class){
                    if(instance == null){
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }
}
