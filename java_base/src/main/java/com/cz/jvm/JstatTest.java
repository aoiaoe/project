package com.cz.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/4/17 : 18:08
 */
public class JstatTest {

    static byte[] arr;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                arr = new byte[1024];
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
    }
}
