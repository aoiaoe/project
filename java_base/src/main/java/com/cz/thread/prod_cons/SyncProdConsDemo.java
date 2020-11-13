package com.cz.thread.prod_cons;

/**
 * 生产者消费者模式1
 * @author alian
 * @date 2020/2/27 下午 5:47
 * @since JDK8
 */
public class SyncProdConsDemo {

    public static void main(String[] args) {
        Good good = new Good();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    good.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    good.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    good.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    good.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"D").start();
    }
}

class Good{

    private int x = 0;

    public synchronized void produce() throws InterruptedException {
        // 判断
        while (x > 0){
            this.wait();
        }
        // 干活
        x = x + 1;
        System.out.println(Thread.currentThread().getName() + "生产1一个,now : " + x);
        // 通知,唤醒全部或者随机唤醒
        this.notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        while (x < 1){
            this.wait();
        }
        x = x - 1;
        System.out.println(Thread.currentThread().getName() + " 消费一个,now : " + x);

        this.notifyAll();
    }
}
