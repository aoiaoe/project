package com.cz.thread;

/**
 * 线程的中断测试
 * @author alian
 * @date 2020/11/12 上午 11:48
 * @since JDK8
 */
public class ThreadInterruptDemo {

    public static void main(String[] args) {
        MyThread thread=new MyThread();
        thread.start();
        // 其作用是中断此线程（此线程不一定是当前线程，而是指调用该方法的Thread实例所代表的线程），
        // 但实际上只是给线程设置一个中断标志，线程仍会继续运行。
        thread.interrupt();

        // 作用是只测试此线程是否被中断 ，不清除中断状态。
        System.out.println("第一次调用thread.isInterrupted()："+thread.isInterrupted());
        System.out.println("第二次调用thread.isInterrupted()："+thread.isInterrupted());

        System.out.println("thread是否存活："+thread.isAlive());

    }
}

class MyThread extends Thread {
    @Override
    public  void run() {

        // 作用是测试当前线程是否被中断（检查中断标志），返回一个boolean并清除中断状态，
        // 第二次再调用时中断状态已经被清除，将返回一个false。
        System.out.println("第一次调用Thread.interrupted()："+ Thread.interrupted());
        System.out.println("第二次调用Thread.interrupted()："+ Thread.interrupted());

        for (int i = 0; i < 10; i++) {
            System.out.println("i="+(i+1));
        }
    }
}
