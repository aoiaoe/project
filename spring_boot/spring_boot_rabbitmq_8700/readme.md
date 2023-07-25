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