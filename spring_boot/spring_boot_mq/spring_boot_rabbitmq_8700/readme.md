### Springboot 整合 RabbitMq
    方式1： 直接整合RabbitMq使用RabbitTemplate发送消息
    方式2:  使用Spring-cloud-stream整合RabbitMq发送消息

    com.cz.spring_boot_rabbitmq.stream包:
        是使用Spirng-cloud-stream发送消息之前以及接受消息之前对消息的重写
    com.cz.spring_boot_rabbitmq.delayqueue.config包:
        是使用RabbitTemplate在发送消息之前及接受消息之前对操作消息的Header
        
死信交换机实现延迟消息有bug
[src/main/java/com/cz/spring_boot_rabbitmq/delayqueue/警告.md]


# 使用Rabbit插件实现延迟消息
    无死信交换机实现延迟消息的队头阻塞问题
    
    插件下载地址: https://github.com/rabbitmq/rabbitmq-delayed-message-exchange
    插件安装教程参考: https://blog.csdn.net/m0_47214030/article/details/131325144

# 消息处理问题
    对于一条消息，如果我们处理不当，可能会导致一些问题:
    1、自动ACK
        a、业务代码抛出异常不处理，则会导致消息丢失
        b、如果不小心，又写了手动ACK代码，会导致多次ACK，消费者重建
    2、手动ACK
        a、忘记ACK，消息无法ack -> 消息积压
        b、业务处理异常, 且未在try或者finally中处理ACK, 消息无法ack -> 消息积压
        c、如果收到消息马上ACK，如果业务处理异常，没有重新发送到mq，或者发送失败，则消息丢失
    3、多次ACK，不管合种方式ACK, 会导致消费者channel关闭, 应用重新拉起消费者