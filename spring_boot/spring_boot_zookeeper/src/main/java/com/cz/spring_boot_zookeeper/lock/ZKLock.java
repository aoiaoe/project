package com.cz.spring_boot_zookeeper.lock;

/**
 * 自定义zk分布式锁：定义通用锁接口
 */
public interface ZKLock {

    /**
     * 加锁
     */
    void lock();

    /**
     * 释放锁
     */
    void unlock();
}
