package com.cz.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jzm
 * @date 2023/5/15 : 15:02
 */
@Slf4j
public class SemaphoreTest {
    // 信号量设置为1，类似于synchronized，但是如果方法有递归，或者调用的代码还需要获取信号量，则会产生死锁
    private Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        SemaphoreTest ins = new SemaphoreTest();
        ins.method();
    }

    public void method(){
        try {
            semaphore.acquire();
            log.info("进入method");
            method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
