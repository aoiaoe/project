package com.cz.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JvisualVmTest {

    public static void main(String[] args) throws Exception {
        System.in.read();
        fillHeap();
    }

    public static void fillHeap() throws InterruptedException {
        List<Capacity> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            TimeUnit.MILLISECONDS.sleep(200);
            list.add(new Capacity());
        }
    }

}

class Capacity{
    private byte[] big = new byte[ 8 * 1024 * 1024];
}
