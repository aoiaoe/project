package com.cz.spring_boot_kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * 使用@KafkaListener注解,可以指定:主题,分区,消费组
 */
@Component
public class KafkaConsumer {

    private static int count = 0;
    @KafkaListener(topics = {"mytopic"})
    public void receive(String message, Acknowledgment acknowledgment) {
        System.out.println("chat--消费消息:" + message);
        acknowledgment.acknowledge();
    }

    public static void main(String[] args) {
        System.out.println(x(8));
        System.out.println(count);
    }

    private static int x(int x){
        count++;
        if(x <=3){
            return 1;
        } else {
            return x(x-2) + x(x-4) + 1;
        }
    }
}