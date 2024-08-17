package com.cz.spring_boot_rabbitmq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AmqpClientTest {

    /**
     * 一个Connection代表一个TCP链接
     * 同一个Connection创建的Channel公用同一个TCP链接(抓包测速)
     * Channel本质上就是一个线程
     * 不同channel之间发送消息可以无序，但是同一个channel里面的消息必有序
     * @throws Exception
     */
    @Test
    public void testAmqpClient() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("tx-gd");
        factory.setPort(5673);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel1 = connection.createChannel();
        Channel channel2 = connection.createChannel();

        String exchangeName = "amqp.client.demo";
        String bindingKey = "amqp.client.demo.key";
        String queueName = "amqp.client.demo.queue";

        channel1.basicPublish(exchangeName, bindingKey, new AMQP.BasicProperties(), "Hello AMQP client".getBytes(StandardCharsets.UTF_8));
        DefaultConsumer c1 = new DefaultConsumer(channel1){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("channel1获取消息" + new String(body));
                channel1.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel1.basicConsume(queueName, false, c1);
        channel1.addConfirmListener((deliveryTag, multiple) -> {}, ((deliveryTag, multiple) -> {}));
        TimeUnit.SECONDS.sleep(5);

        channel2.basicPublish(exchangeName, bindingKey, new AMQP.BasicProperties(), "Hello AMQP client Channel2".getBytes(StandardCharsets.UTF_8));
        DefaultConsumer c2 = new DefaultConsumer(channel2){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("channel2获取消息" + new String(body));
                channel2.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel2.basicConsume(queueName, false, c2);
        TimeUnit.SECONDS.sleep(10000);
        channel1.close();
        channel2.close();
        connection.close();

    }

}
