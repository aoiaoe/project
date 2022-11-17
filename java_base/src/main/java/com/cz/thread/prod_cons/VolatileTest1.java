package com.cz.thread.prod_cons;

import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Data
class X{

    volatile Y xx = new Y();
}
@Data
class Y{
    Boolean flag = true;
}
/**
 * System.out.println 会影响内存可见性
 * 因为该方法内部使用了 synchronized 关键字加锁
 * 使用了synchronized上锁这个操作后线程会做以下操作：
 * 1.获得同步锁
 * 2.清空工作内存
 * 3.从主内存中拷贝对象副本到本地内存
 * 4.执行代码（打印语句或加加操作）
 * 5.刷新主内存数据
 * 6.释放同步锁
 *
 * 对于volatile修饰的引用变量， 保证引用对象的可见性
 * 但是不保证对象属性的可见性(测试比较麻烦，在多次修改值的情况下容易出现，参考 VolatileReferenceTest.java)
 * 如果需要保证引用对象属性的可见性，2个方案
 * 1、给对象属性增加volatile修饰符
 * 2、使用 synchronized 加锁
 */
@Data
public class VolatileTest1 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100000000; i++) {
            test();
            System.out.println(i);
        }
    }

    public static int test() throws InterruptedException {
        CountDownLatch cd = new CountDownLatch(1);
        X x = new X();
        new Thread(() -> {
            boolean flag1 = true;
            while (x.xx.getFlag()){
                if(flag1){
                    new Thread(() -> {
                        x.xx.setFlag(false);
                    }).start();
                    flag1 = false;
                }
            }
            cd.countDown();
        }, "A").start();

//        try {
//            TimeUnit.MILLISECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(() -> {
//            x.xx.setFlag(false);
//        }, "B").start();
        cd.await();
        return 1;
    }
}
