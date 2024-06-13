package com.cz.rocket.service;

import com.cz.rocket.entity.Order;
import com.cz.rocket.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class OrderService {

    public static final String ORDER_TX_PRODUCER = "OrderTxProducer";
    public static final String ORDER_ID = "OrderId";

    @Autowired
    public OrderMapper orderMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private IdUtils idUtils;

    public boolean createOrder(Order order){
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            // 先获取订单id
            order.setId(idUtils.id());
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put(ORDER_ID, order.getId());
            MessageHeaders headers = new MessageHeaders(objectObjectHashMap);
            Message message  = MessageBuilder.createMessage(order.getId(), headers);
            TransactionSendResult sendResult = this.rocketMQTemplate.sendMessageInTransaction("OrderTxTopic", message, order);
            log.info("发送结果：{}", sendResult);
        } finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
        return true;
    }
}
