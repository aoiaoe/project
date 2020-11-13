package com.cz.collection.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者模式
 *      阻塞队列
 *      方法\处理方式     抛异常           返回特殊值       一直阻塞         超时退出
 *      插入             add(e)          offer(e)         put(e)         offer(e, time, timeUnit)
 *      移除             remove(e)       poll(e)          take(e)        poll(time, timeUnit)
 *      检查             element(e)      peek(e)          不可用         不可用
 * @author alian
 * @date 2020/2/27 下午 3:22
 * @since JDK8
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        ShareDataBlockingQueue shareData = new ShareDataBlockingQueue(queue);

        new Thread(() -> {
            try {
                shareData.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(() -> {
            try {
                shareData.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Cons1").start();
        new Thread(() -> {
            try {
                shareData.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Cons2").start();

        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        shareData.stop();

    }

}

class ShareDataBlockingQueue {

    public ShareDataBlockingQueue(BlockingQueue<Integer> queue) {
        this.blockingQueue = queue;
    }

    private volatile boolean flag = true;
    private BlockingQueue<Integer> blockingQueue = null;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public void prod() throws InterruptedException {
        Integer data = null;
        while (flag) {
            data = atomicInteger.incrementAndGet();
            blockingQueue.offer(data, 200, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + "\t 生产 \t" + data);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "大老板叫停生产: flag:" + flag);
    }

    public void consume() throws InterruptedException {
        Integer data = null;
        while (flag) {
            data = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (data == null) {
                System.out.println(Thread.currentThread().getName() + "\t 未消费到数据退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费 \t" + data);
        }
    }

    public void stop() {
        flag = false;
    }

}