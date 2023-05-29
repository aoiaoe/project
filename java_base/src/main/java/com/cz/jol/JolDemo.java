package com.cz.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.nio.ByteOrder;
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

    private int a = 1;

    public static void main(String[] args) {
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

    public static void displayLayout(JolDemo obj) {
        String info = ClassLayout.parseInstance(obj).toPrintable();
        log.info("内存布局:{}", info);
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
