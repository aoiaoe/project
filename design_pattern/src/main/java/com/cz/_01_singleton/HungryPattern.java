package com.cz._01_singleton;

/**
 * 饿汉式
 * 优点：线程安全，不用加同步锁，因此在高并发时调用效率高。
 * <p>
 * 缺点：不能懒加载，如果不使用该类的实例，浪费内存资源。
 */
public class HungryPattern {

    private HungryPattern() {
    }

    public static HungryPattern instance = new HungryPattern();

    public static HungryPattern getInstance() {
        return instance;
    }
}
