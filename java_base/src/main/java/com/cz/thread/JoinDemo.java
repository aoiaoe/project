package com.cz.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author alian
 * @date 2020/4/1 上午 11:14
 * @since JDK8
 */
public class JoinDemo {

    final static int LOOP = 1000;

    /**
     * 线程不安全的加/减
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        Producer producer = new Producer(counter);
        Consumer consumer = new Consumer(counter);

        producer.start();
        consumer.start();

        System.out.println("main join producer");
        producer.join();
        System.out.println("main join consumer");
        consumer.join();

        System.out.println(counter.getCount());

    }

}

class Counter {

    volatile int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(){
        count += 1;
    }

    public void dec(){
        count -= 1;
    }
}

class Consumer extends Thread{

    Counter counter;

    public Consumer(Counter counter){
        this.counter = counter;
    }


    @Override
    public void run() {
        System.out.println("consumer start :" + counter.count);
        for(int j = 0;j < JoinDemo.LOOP;j++){
            counter.dec();
        }
        System.out.println("consumer end :" + counter.count);
    }
}


class Producer extends Thread{

    Counter counter;

    public Producer(Counter counter){
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.println("producer start :" + counter.count);
        for(int i = 0;i < JoinDemo.LOOP;++i){
            counter.add();
        }
        System.out.println("producer end :" + counter.count);
        try {
            TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
    }
}
