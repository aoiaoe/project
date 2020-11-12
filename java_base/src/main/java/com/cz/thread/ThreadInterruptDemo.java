package com.cz.thread;

/**
 * 线程的中断测试
 * interrupt（）是给线程设置中断标志；interrupted（）是检测中断并清除中断状态；isInterrupted（）只检测中断。
 * 还有重要的一点就是interrupted（）作用于当前线程，
 * interrupt（）和isInterrupted（）作用于此线程，即代码中调用此方法的实例所代表的线程。
 *
 * interrupt()：打一个中断标记，并没有实际去中断一个线程。
 * isInterrupted()：如果调用了interrupt()方法，则这里会输出true，表示当前线程被中断过。
 * interrupted()：静态方法。如果调用了interrupt()方法，则这里会输出true，表示当前线程被中断过，但是这个方法比上面的isInterrupted()方法多做了一件事，那就是线程复位，
 *                  也就是说，连续两次调用interrupted()方法后，第一次为true，第二次就会变回false。
 *
 * 为什么需要对线程复位？ 我们设想这么一种场景，假如我们规定收到3次线程中断要求的时候，即使线程没有执行完毕这时候也需要直接返回，
 * 那么线程如果不复位，我们就没办法知道当前线程进行过多少次中断(不考虑同时多次中断的情况)，
 * 因为中断过一次，一直是true；而有了线程复位，我们就只需要判断3次都是true就说明至少被中断过3次。
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
