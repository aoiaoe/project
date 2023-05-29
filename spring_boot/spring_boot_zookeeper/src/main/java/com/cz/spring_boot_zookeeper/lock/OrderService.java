package com.cz.spring_boot_zookeeper.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderService {

    // ZkClient分布式锁
    private ZKLock lock = new ZkClientLock();

    /**
     * 创建订单号，模拟业务
     */
    public void createOrderNum() {
        lock.lock();
        String orderNum = generateOrderNum();
        System.out.println(Thread.currentThread().getName() + "创建了订单号：[" + orderNum + "]");
        lock.unlock();
    }


    /**
     * 生成时间格式的订单编号
     * @return
     */
    private static int orderNum = 0;
    public String generateOrderNum() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        return simpleDateFormat.format(new Date()) + ++orderNum;
    }

}
