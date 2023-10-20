package com.cz.rocket.quickstart;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

public class MyProducer {

    public static final String TOPIC = "jzm-topic";

    public static void main(String[] args) throws Exception {
        new MyProducer().send();
    }
    public void send() throws Exception{
        // 实例化生产者，并指定生产组名称
        DefaultMQProducer producer = new DefaultMQProducer("cz_group1");
        // 指定nameServer地址
        producer.setNamesrvAddr("192.168.18.203:9876");

        // 对生产者进行初始化，才可以发送消息
        producer.start();
        // 创建消息
        Message message = new Message(TOPIC,
                "Hello Jzm1122".getBytes(RemotingHelper.DEFAULT_CHARSET));

        // 同步发送
//        SendResult send = producer.send(message, 1000);

        // 异步发送
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败:" + throwable.getMessage());
            }
        });

        TimeUnit.SECONDS.sleep(1);

        // 关闭生产者
        producer.shutdown();
    }
}
