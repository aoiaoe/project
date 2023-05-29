package com.cz.spring_boot_zookeeper.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * 基于ZkClient客户端实现锁：
 */
public class ZkClientLock extends AbstractZKLock {

    private CountDownLatch cdl = null;

    @Override
    protected ZkClient createClient() throws Exception{
        return new ZkClient("tx-sh:2181", 5000, 15000, new MyZkSerializer());
    }

    /**
     * 尝试获取锁
     * @return
     */
    @Override
    protected boolean tryLock() {
        try {
            // 加锁：创建临时节点，创建成功表示加锁成功，否则加锁失败。
            // zookeeper 的特性，节点名不能重复，否则创建失败。
            if(zkClient.exists(path)) {
                zkClient.delete(path);
            }
            //创建临时节点
            zkClient.createEphemeral(path);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 等待获取锁:
     * 等前面那个获取锁成功的客户端释放锁
     * 没有获取到锁的客户端都会走到这里
     * 1、没有获取到锁的要注册对 path节点的watcher
     * 2、这个方法需要等待
     */
    @Override
    protected void waitForLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            // 一旦 path节点被删除（释放锁）以后，就会触发这个方法
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 让等待的代码不再等待了
                // 即 waitforlock方法执行结束，重新去获取锁
                if (cdl != null) {
                    cdl.countDown();
                }
            }
        };
        // 注册对 path节点的watcher
        zkClient.subscribeDataChanges(path, iZkDataListener);

        // 等待
        if (zkClient.exists(path)) {
            cdl = new CountDownLatch(1);
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 取消该客户端的订阅关系
        zkClient.unsubscribeDataChanges(path, iZkDataListener);
    }
}
