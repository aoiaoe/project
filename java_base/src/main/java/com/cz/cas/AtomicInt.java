package com.cz.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

public class AtomicInt {

    private volatile int count;
    private static final Unsafe unsafe;
    private static final long address;

    static {
        try {

            // 使用反射从Unsafe类中回去unsafe对象
            // 因为Unsafe.getUnsafe()这个Api会判断是否系统加载器，进行权限控制, 获取不到Unsafe对象
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            address = unsafe.objectFieldOffset(AtomicInt.class.getDeclaredField("count"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public int getAndAdd(int x){
        return unsafe.getAndAddInt(this, address, x);
    }

    public int unsafeGetAndAdd(int x){
        return count += x;
    }

    public int get(){
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(5);
        AtomicInt atomicInt = new AtomicInt();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
//                    atomicInt.getAndAdd(1);
                    atomicInt.unsafeGetAndAdd(1);
                }
                cdl.countDown();
            }).start();
        }

        cdl.await();
        System.out.println(atomicInt.get());
    }
}
