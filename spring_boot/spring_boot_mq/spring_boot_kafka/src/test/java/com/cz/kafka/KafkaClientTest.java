package com.cz.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class KafkaClientTest {

    @Test
    public void testProduce() throws ExecutionException, InterruptedException {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "192.168.18.203:9092");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord("mytopic", String.valueOf(i), i + " -> 当前时间:" + new Date().toString());
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            System.out.println("消息发送至 主题:" + metadata.topic() + " 分区：" + metadata.partition() + " 偏移量" + metadata.offset());
        }
        producer.close();
    }

    @Test
    public void testConsumer(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.18.203:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "applog1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(props);
        List<String> topics = new ArrayList<>();
        topics.add("mytopic");
        kafkaConsumer.subscribe(topics);
        while (true) {
            // 尝试拉取三秒
            // 批量拉取
            ConsumerRecords<String, String> poll = kafkaConsumer.poll(3000);
            for (ConsumerRecord<String, String> record : poll) {
                System.out.println("主题:" + record.topic() +
                        " 分区:" + record.partition() +
                        " 偏移量:" + record.offset() +
                        " key:" + record.key() +
                        " value:" + record.value());
            }
        }
    }

}
