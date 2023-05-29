package com.cz.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/5/15 : 15:08
 */
@Slf4j
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String name = exchanger.exchange(Thread.currentThread().getName());
                log.info("name:{}", name );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                String name = exchanger.exchange(Thread.currentThread().getName());
                log.info("name:{}", name );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

}
