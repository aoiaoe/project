### Springboot 整合 RabbitMq
    方式1： 直接整合RabbitMq使用RabbitTemplate发送消息
    方式2:  使用Spring-cloud-stream整合RabbitMq发送消息

    com.cz.spring_boot_rabbitmq.stream包:
        是使用Spirng-cloud-stream发送消息之前以及接受消息之前对消息的重写
    com.cz.spring_boot_rabbitmq.delayqueue.config包:
        是使用RabbitTemplate在发送消息之前及接受消息之前对操作消息的Header
        
死信交换机实现延迟消息有bug
[src/main/java/com/cz/spring_boot_rabbitmq/delayqueue/警告.md]