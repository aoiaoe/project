package com.cz.rocket.config;

import com.cz.rocket.service.OrderService;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

//@Configuration
public class RocketMqConfig {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private RocketMQLocalTransactionListener orderTransactionListener;

//    @PostConstruct
//    public void configTxProducerListener(){
//        rocketMQTemplate.createAndStartTransactionMQProducer(OrderService.ORDER_TX_PRODUCER,
//                orderTransactionListener,
//                Executors.newSingleThreadExecutor(), null);
//    }

}
