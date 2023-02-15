package com.cz.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * static变量java8位于Heap中， 测试
 * jvm 参数  -Xms20m -Xmx20m -XX:NewRatio=8
 */
public class StaticInHeapTest {

    static List<byte[]> b = new ArrayList<>();

    static {
        for (int i = 0; i < 20; i++) {
            b.add(new byte[1024 * 1024]);
        }
    }

    public static void main(String[] args) {

    }
}
