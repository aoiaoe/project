package com.cz.rocket.consumer;

import com.cz.rocket.controller.MqController;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 测试队列: rk-test-5part
 * 创建命令: mqadmin updateTopic -c DefaultCluster -n localhost:9876 -t rk-test-5part -r 5 -w 5
 * 队列: rk-test-5part 有5个分区
 * 测试结论:
 *      下面的每一个Bean是一个消费者,但是每一个消费者可以有多个线程组成
 *      论据1: 向队列rk-test-5part发送了100条消息，五个分区,每个分区20条,
 *          假设如果每一个线程是一个消费者, 所有消费者在同一个消费者组, 那么按RocketMq设计,只有5个消费者被分配到消费分区
 *          如果只启动下面的第一个消费者Bean, 会发现: 所有的线程都启动，并接收到消息打印
 *          故，一个Bean才是一个消费者，而一个消费者可以包含多个线程并行消费
 *      论据2: 如果将下面的消费者Bean的注释都打开，则是6个消费者, 然后使用只有5个分区的队列进行测试后，
 *             会发现，Bean SpringbootConsumer6,也就是第六个消费者没有消费, 因为分区只有5个，但是有6个消费者
 *             所以会有一个消费者没有被分配分区
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 10)
public class SpringbootConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        }catch (Exception e){

        }
        log.info("接收到消息：{}-{}", Thread.currentThread().getName(), s);
    }
}

//
//@Component
//@Slf4j
//@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 10)
//class SpringbootConsumer2 implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String s) {
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        }catch (Exception e){
//
//        }
//        log.info("XXXXX>接收到消息：{}-{}", Thread.currentThread().getName(), s);
//    }
//}
//
//
//@Component
//@Slf4j
//@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 10)
//class SpringbootConsumer3 implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String s) {
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        }catch (Exception e){
//
//        }
//        log.info("---->接收到消息：{}-{}", Thread.currentThread().getName(), s);
//    }
//}
//
//@Component
//@Slf4j
//@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 10)
//class SpringbootConsumer4 implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String s) {
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        }catch (Exception e){
//
//        }
//        log.info("4444>接收到消息：{}-{}", Thread.currentThread().getName(), s);
//    }
//}
//
//@Component
//@Slf4j
//@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 10)
//class SpringbootConsumer5 implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String s) {
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        }catch (Exception e){
//
//        }
//        log.info("5555>接收到消息：{}-{}", Thread.currentThread().getName(), s);
//    }
//}

//@Component
//@Slf4j
//@RocketMQMessageListener(consumerGroup = "jzm-group", topic = MqController.TOPIC,consumeThreadMax = 10)
//class SpringbootConsumer6 implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String s) {
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        }catch (Exception e){
//
//        }
//        log.info("6666>接收到消息：{}-{}", Thread.currentThread().getName(), s);
//    }
//}
