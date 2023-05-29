package com.cz.spring_boot_zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * 抽象锁对象：用到了模板方法设计模式，具体抽象方法由子类实现
 */
public abstract class AbstractZKLock implements ZKLock {

    protected static String path = "/lock";

    protected ZkClient zkClient = null;

    public AbstractZKLock() {
        initClient();
    }

    public void initClient(){
        try {
            zkClient = createClient();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化 zk客户端连接失败，errorMessage=" + e.getMessage());
        }
    }

    /**
     * 交给子类创建
     * @return
     */
    protected abstract ZkClient createClient()  throws Exception;

    /**
     * lock方法（模板方法设计模式）：获取锁的方法
     *   1.如果锁获取成功，那么业务代码继续往下走。
     *   2.如果锁获取失败，lock方法需要等待重新获取锁
     *      2.1等待到了前面那个获取锁的客户端释放锁以后（zk监听机制），
     *      2.2然后再去重新获取锁
     */
    @Override
    public void lock() {
        // 尝试去获取锁
        if(tryLock()){
            System.out.println(Thread.currentThread().getName() + "--->获取锁成功！");
        }else {
            // 获取失败，在这里等待
            waitForLock();
            // 重新获取锁
            lock();
        }
    }

    @Override
    public void unlock() {
        // 因为加锁创建的是临时节点，所以会话关闭，临时节点就删除了，即释放了锁
        zkClient.close();
    }

    /**
     * 获取锁
     * @return
     */
    protected abstract boolean tryLock();

    /**
     * 获取失败，等待其他释放锁，重新获取锁
     */
    protected abstract void waitForLock();
}
