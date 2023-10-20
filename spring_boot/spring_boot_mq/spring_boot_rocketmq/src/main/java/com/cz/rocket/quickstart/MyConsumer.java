package com.cz.rocket.quickstart;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.Set;

public class MyConsumer {

    public static void main(String[] args) throws Exception {
        // 拉取消息客户端
//        pullConsumer();

        pushConsumer();
    }

    public static void pushConsumer() throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("jzm_group1");
        consumer.setNamesrvAddr("192.168.18.203:9876");
//        consumer.subscribe(MyProducer.TOPIC, "*");

        consumer.subscribe("test", "*");

        consumer.setConsumeThreadMin(1);
        consumer.setMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt messageExt : list) {
                    System.out.println("获取到消息:" + new String(messageExt.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
    }

    public static void pullConsumer() throws Exception{
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("jzm_group");

        consumer.setNamesrvAddr("192.168.18.203:9876");

        consumer.start();

        // 获取指定主题的队列集合
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(MyProducer.TOPIC);
        for (MessageQueue messageQueue : messageQueues) {
            PullResult pull = consumer.pull(messageQueue, "*", 0, 10);
            List<MessageExt> msgFoundList = pull.getMsgFoundList();

            if(msgFoundList == null) continue;
            for (MessageExt messageExt : msgFoundList) {
                System.out.println("获取到消息:" + new String(messageExt.getBody()));
            }
        }

//        consumer.shutdown();
    }
}
