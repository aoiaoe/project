package com.cz.heap;

import java.util.concurrent.TimeUnit;

/**
 * -Xms20m -Xmx20m -XX:NewRatio=8 -XX:+PrintGCDetails
 * @author alian
 * @date 2021/2/8 上午 11:56
 * @since JDK8
 */
public class HeapJvmConfigDemo {

    public static void main(String[] args) {

        int x = 10;
        System.gc();

        try {
            TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

    }
}
