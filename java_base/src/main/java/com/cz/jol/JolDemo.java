package com.cz.jol;

import cn.hutool.core.util.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 进行此实验，需要如下JVM参数：
 * -XX:-UseCompressedOops -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 * 并且还需要前置知识：
 *  1、字节序，大端存储：高位字节在前面  小端存储：低位字节在前面
 *      jvm字节序为：小端存储
 *  2、对象头的锁标志位
 *
 * //关闭偏向锁
 * -XX:-UseBiasedLocking
 * //开启偏向锁
 * -XX:+UseBiasedLocking
 *
 * @author jzm
 * @date 2022/11/21 : 16:36
 */
@Slf4j
public class JolDemo {

    private int a = 1231415124;

    public static void main(String[] args) {
//        test1();

        test2();
    }

    public static void test1(){
        // 字节序是大端存储还是小端存储
        // 参考： https://www.cnblogs.com/wuyuegb2312/archive/2013/06/08/3126510.html
        // 参考： https://blog.csdn.net/dhaibo1986/article/details/108453102
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        log.info("[java]是小端存储，意思是字节顺序是倒叙的，故下列jol打印的锁对象内存布局，打印的第一个字节其实是最后一个字节");
        log.info("本机字节序：{}", byteOrder);
        JolDemo obj = new JolDemo();
        log.info("初始化内存布局: 此处由于jvm参数设置偏向锁延迟为0，偏向main线程");
        displayLayout(obj);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("5秒后 线程A启动，查看内存布局 A线程获取锁对象obj， 并且不存在竞争，持有obj的偏向锁--------------------------");
        new MyThread(obj, "A").start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("10秒main线程查看内存布局");
        displayLayout(obj);
        log.info("10秒后线程B启动，查看内存布局，A线程已经释放锁对象，B线程获取锁对象obj，不存在竞争，" +
                "但是由于锁对象obj的mark word有偏向于线程A的标志，故锁升级为轻量级锁--------------------------");
        new MyThread(obj, "B").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("3秒后线程C启动，查看内存布局，由于B线程还持有锁对象obj，C线程也竞争此对象的锁，导致轻量级锁升级为重量级锁--------------------------");
        new MyThread(obj, "C").start();
    }

    /**
     * 11:07:06.817 [main] INFO com.cz.jol.JolDemo - [java]是小端存储，意思是字节顺序是倒叙的，故下列jol打印的锁对象内存布局，打印的第一个字节其实是最后一个字节
     * 11:07:06.820 [main] INFO com.cz.jol.JolDemo - 本机字节序：LITTLE_ENDIAN
     * 11:07:06.821 [main] INFO com.cz.jol.JolDemo - 初始化内存布局: 此处由于jvm参数设置偏向锁延迟为0，偏向main线程
     * 11:07:08.962 [main] INFO com.cz.jol.JolDemo - 内存布局:com.cz.jol.JolDemo object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           98 3b da 25 (10011000 00111011 11011010 00100101) (635059096)
     *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *      16     4    int JolDemo.a                                 1
     *      20     4        (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 11:07:13.965 [main] INFO com.cz.jol.JolDemo - 5秒后 线程A启动，查看内存布局 A线程获取锁对象obj， 并且不存在竞争，持有obj的偏向锁--------------------------
     * 11:07:13.970 [A] INFO com.cz.jol.JolDemo - 内存布局:com.cz.jol.JolDemo object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 c0 a9 29 (00000101 11000000 10101001 00101001) (698990597)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           98 3b da 25 (10011000 00111011 11011010 00100101) (635059096)
     *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *      16     4    int JolDemo.a                                 1
     *      20     4        (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 11:07:14.977 [A] INFO com.cz.jol.JolDemo - 内存布局:com.cz.jol.JolDemo object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 c0 a9 29 (00000101 11000000 10101001 00101001) (698990597)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           98 3b da 25 (10011000 00111011 11011010 00100101) (635059096)
     *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *      16     4    int JolDemo.a                                 1
     *      20     4        (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 11:07:15.990 [A] INFO com.cz.jol.JolDemo - 内存布局:com.cz.jol.JolDemo object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           05 c0 a9 29 (00000101 11000000 10101001 00101001) (698990597)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           98 3b da 25 (10011000 00111011 11011010 00100101) (635059096)
     *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *      16     4    int JolDemo.a                                 1
     *      20     4        (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 11:07:16.972 [main] INFO com.cz.jol.JolDemo - 10秒main线程查看内存布局
     * 11:07:16.972 [main] INFO com.cz.jol.JolDemo - 3秒后main线程获取hashcode, 导致锁对象的状态发生改变，锁升级为重量级锁
     * 11:07:17.007 [A] INFO com.cz.jol.JolDemo - 内存布局:com.cz.jol.JolDemo object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           7a fe 36 26 (01111010[此处锁标志位已经升级为重量级锁] 11111110 00110110 00100110) (641138298)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           98 3b da 25 (10011000 00111011 11011010 00100101) (635059096)
     *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *      16     4    int JolDemo.a                                 1
     *      20     4        (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * 11:07:18.021 [A] INFO com.cz.jol.JolDemo - 内存布局:com.cz.jol.JolDemo object internals:
     *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
     *       0     4        (object header)                           7a fe 36 26 (01111010 11111110 00110110 00100110) (641138298)
     *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *       8     4        (object header)                           98 3b da 25 (10011000 00111011 11011010 00100101) (635059096)
     *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
     *      16     4    int JolDemo.a                                 1
     *      20     4        (loss due to the next object alignment)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     *
     * Process finished with exit code 0
     */
    public static void test2(){
        // 字节序是大端存储还是小端存储
        // 参考： https://www.cnblogs.com/wuyuegb2312/archive/2013/06/08/3126510.html
        // 参考： https://blog.csdn.net/dhaibo1986/article/details/108453102
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        log.info("[java]是小端存储，意思是字节顺序是倒叙的，故下列jol打印的锁对象内存布局，打印的第一个字节其实是最后一个字节");
        log.info("本机字节序：{}", byteOrder);
        JolDemo obj = new JolDemo();
        System.out.println("hashcode: -----------> " + binaryHash(obj));
        log.info("初始化内存布局: 此处由于jvm参数设置偏向锁延迟为0，偏向main线程");
        displayLayout(obj);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("5秒后 线程A启动，查看内存布局 A线程获取锁对象obj， 并且不存在竞争，持有obj的偏向锁--------------------------");
        new MyThread(obj, "A").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("10秒main线程查看内存布局");
        System.out.println("hashcode: -----------> " + binaryHash(obj));
        log.info("3秒后main线程获取hashcode, 因为偏向锁状态存储的是偏向的线程id,没有位置保存hashcode, 所以会撤销偏向锁状态," +
                "除非重写了hashcode()方法，如果不重写hashcode()方法会将hash写入对象头重, 反之则不写");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void displayLayout(JolDemo obj) {
        String info = ClassLayout.parseInstance(obj).toPrintable();
        log.info("内存布局:{}", info);
    }

    /**
     * 输出二进制、小端模式的hashCode
     */
    public static String binaryHash(Object obj)
    {
//对象的原始hashCode，Java默认为大端模式
        int hashCode = obj.hashCode();
//转成小端模式的字节数组
        byte[] hashCode_LE = ByteUtil.intToBytes(hashCode, ByteOrder.LITTLE_ENDIAN);
        StringBuffer buffer=new StringBuffer();
        for (byte b:hashCode_LE)
        {
//转成二进制形式的字符串
            buffer.append(bytesToBinary(b));
            buffer.append( " ");
        }
        return buffer.toString();
    }

    public static String bytesToBinary(byte bytes) {
        StringBuilder binaryBuilder = new StringBuilder();
        int val = bytes;
        for (int i = 0; i < 8; i++) {
            binaryBuilder.append((val & 128) == 0 ? 0 : 1);
            val <<= 1;
        }
        return binaryBuilder.toString();
    }

    static class MyThread extends Thread {

        private JolDemo obj;
        private String name;

        public MyThread(JolDemo obj, String name) {
            super(name);
            this.obj = obj;
        }

        @Override
        public void run() {
            synchronized (obj) {

                for (int i = 0; i < 5; i++) {
                    JolDemo.displayLayout(obj);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
