package com.cz.thread;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * ThreadLocal中，获取到线程私有对象是通过线程持有的一个threadLocalMap，然后传入ThreadLocal当做key获取到对象的，
 * 这时候就有个问题，如果你在使用完ThreadLocal之后，将其置为null，
 * 这时候这个对象并不能被回收，因为他还有 ThreadLocalMap->entry->key的引用，
 * 直到该线程被销毁，但是这个线程很可能会被放到线程池中不会被销毁，这就产生了内存泄露，
 * jdk是通过弱引用来解决的这个问题的，entry中对key的引用是弱引用，
 * 当你取消了ThreadLocal的强引用之后，他就只剩下一个弱引用了，所以也会被回收。
 *
 * 所以ThreadLocal通常应该为 全局常量
 * <p>
 * 作者：吕清海
 * 链接：https://www.zhihu.com/question/37401125/answer/337717256
 * 来源：知乎
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * // jvm 参数 -Xms10m -Xmx10m
 * @author alian
 * @date 2020/10/21 下午 5:28
 * @since JDK8
 */
@Slf4j
public class ThreadLocalDemo {

    private static int _1M = 1024*1024;
    public static void main(String[] args) throws Exception {
        testMethodLocalTLMemoryLeak();
    }


    /**
     * 如果ThreadLocal是局部变量， 强引用被去除之后(通常是 == null操作, 也可能是退出方法调用)
     * 垃圾回收会把ThreadLocal变量回收，从而产生内存泄漏
     * 不过ThreadLocal的get方法内部，有去除内存泄漏产生的无效数据的操作
     *
     * 所以ThreadLocal通常应该是全局常量
     * @throws Exception
     */
    public static void testMethodLocalTLMemoryLeak() throws Exception {
        ThreadLocal<String> tl = new ThreadLocal<String>(){
            @Override
            protected void finalize() throws Throwable {
                log.info("tl被回收啦。。。。。。");
            }
        };
        tl.set("asc");
        log.info("tl中的值:{}", tl.get());
        // jvm 参数 -Xms10m -Xmx10m
        // 由于tl是强引用, 经过下面的4次内存分配gc过后，也不会被回收
        for (int i = 0; i < 6; i++) {
            allocate2M();
        }
        Thread thread = Thread.currentThread();
        Class<? extends Thread> aClass = thread.getClass();
        Field threadLocals = aClass.getDeclaredField("threadLocals");
        threadLocals.setAccessible(true);
        // 使用debug观察下面的tlMap对象内容，可以发现对象中还存在tl的引用
        Object tlMap = threadLocals.get(thread);

        // 释放掉tl的强引用，这是只在Thread的threadLocals属性中，存在一个弱引用
        tl = null;
        // jvm 参数 -Xms10m -Xmx10m
        // 如果释放掉tl的强引用，经过下面的4次循环分配2M字节数组，那肯定会经过垃圾回收，将tl所在的弱引用给回收
        for (int i = 0; i < 6; i++) {
            allocate2M();
        }
        // 使用debug观察下面的tlMap对象内容就可以发现内存泄漏了
        tlMap = threadLocals.get(thread);
        System.out.println(tlMap);
    }

    public static void allocate2M(){
        byte[] s = new byte[2 * _1M];
    }
}
