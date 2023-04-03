package com.cz.thread;

import lombok.extern.slf4j.Slf4j;

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
       new VolatileTest().parkTest();

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
}
