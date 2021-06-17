package com.cz.thread.prod_cons;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 生产者消费者模式
 * 阻塞队列
 * 方法\处理方式     抛异常           返回特殊值       一直阻塞         超时退出
 * 插入             add(e)          offer(e)         put(e)         offer(e, time, timeUnit)
 * 移除             remove(e)       poll(e)          take(e)        poll(time, timeUnit)
 * 检查             element(e)      peek(e)          不可用         不可用
 *
 * @author alian
 * @date 2020/2/28 上午 10:01
 * @since JDK8
 */
public class BlockQueueProdConsDemo {

}

class ShareData {

    private volatile boolean flag = true;

    private BlockingQueue<Integer> queue;

    public ShareData(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void produce() throws InterruptedException {
        while (flag) {
            queue.put(1);
            System.out.println(Thread.currentThread().getName() + "生产 1 now :" + queue.size());
        }

        System.out.println("老板叫停 flag=" + flag);
    }

    public void consume() throws InterruptedException {
        while (flag) {
            Integer poll = queue.poll(2, TimeUnit.SECONDS);

            System.out.println(Thread.currentThread().getName() + "消费 1 now :" + queue.size());
        }
    }

    public void stop() {
        flag = false;
    }
}
