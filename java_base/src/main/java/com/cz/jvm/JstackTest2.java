package com.cz.jvm;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 * Found one Java-level deadlock:
 * =============================
 * "B":
 *   waiting to lock monitor 0x0000000025b01338 (object 0x00000007161c3370, a java.lang.Object),
 *   which is held by "A"
 * "A":
 *   waiting to lock monitor 0x0000000025afeaa8 (object 0x00000007161c3380, a java.lang.Object),
 *   which is held by "B"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "B":
 *         at com.cz.jvm.JstackTest2.lambda$main$1(JstackTest2.java:32)
 *         - waiting to lock <0x00000007161c3370> (a java.lang.Object)
 *         - locked <0x00000007161c3380> (a java.lang.Object)
 *         at com.cz.jvm.JstackTest2$$Lambda$2/1940447180.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 * "A":
 *         at com.cz.jvm.JstackTest2.lambda$main$0(JstackTest2.java:20)
 *         - waiting to lock <0x00000007161c3380> (a java.lang.Object)
 *         - locked <0x00000007161c3370> (a java.lang.Object)
 *         at com.cz.jvm.JstackTest2$$Lambda$1/1811075214.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 */
public class JstackTest2 {

    private static Object obj1 = new Object();
    private static Object obj2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (obj1){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2){

                }
            }
        }, "A").start();
        new Thread(() -> {
            synchronized (obj2){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1){

                }
            }
        }, "B").start();
    }

}
