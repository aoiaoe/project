package com.cz.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author alian
 * @date 2021/3/4 下午 5:51
 * @since JDK8
 */
public class Source implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;

    public Source(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch) {
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
    }

    static int x = 0;
    static int count = 100;
    static int threadNum = 200;

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < count; i++) {
            x++;
            System.out.println(x);
        }
        countDownLatch.countDown();
    }

    static int y = 0;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(0);
        Source source = new Source(cyclicBarrier, countDownLatch);

        for (int i = 0; i < threadNum; i++) {
            new Thread(source, "thread_" + i).start();
        }

        countDownLatch.await();
        System.out.println("最终" + x);

    }
}
