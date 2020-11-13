package com.cz.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Print{

    private String flag = "A";
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void print(){
        lock.lock();
        try {
            String name = Thread.currentThread().getName();
            while(!flag.equals(name)){
                condition.await();
            }
            System.out.println(name);
            if(flag.equals("A")) flag = "B";
            else if (flag.equals("B")) flag = "C";
            else if(flag.equals("C")) flag = "A";
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

/**
 * @author alian
 * @date 2020/1/3 下午 2:33
 * @since JDK8
 */
public class PrintABCInOrder {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                print.print();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                print.print();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                print.print();
            }
        },"C").start();
    }
}
