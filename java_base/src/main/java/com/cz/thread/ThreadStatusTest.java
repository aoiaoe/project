package com.cz.thread;

import java.util.concurrent.TimeUnit;

/**
 * jps
 * jstack {pid}
 */
public class ThreadStatusTest {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "STATUS_01").start();  //阻塞状态

        new Thread(() -> {
            while (true) {
                synchronized (ThreadStatusTest.class) {
                    try {
                        ThreadStatusTest.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "STATUS_02").start(); //阻塞状态

        // 两个线程持有同一把锁
        new Thread(new BlockedDemo(), "BLOCKED-DEMO-01").start();
        new Thread(new BlockedDemo(), "BLOCKED-DEMO-02").start();

    }

    // 模拟Blocked状态
    static class BlockedDemo extends Thread {
        @Override
        public void run() {
            synchronized (BlockedDemo.class) {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
//
// 作者：autumn
// 链接：https://juejin.im/post/6860071235339059213
// 来源：掘金
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。