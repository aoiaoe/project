package com.cz.jvm;

import java.util.ArrayList;

public class TestGCLog {
    private static final int _1MB = 124 * 1024;

    /**
     * -Xms100M -Xmx100M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -Xloggc:g:/logs/gc.log
     **/
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0; i < 2000; i++) {
            byte[] arr = new byte[1024 * 1024];
            list.add(arr);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
