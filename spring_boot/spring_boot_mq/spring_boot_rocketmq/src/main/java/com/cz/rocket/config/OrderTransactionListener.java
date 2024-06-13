package com.cz.rocket.config;

import com.cz.rocket.entity.Order;
import com.cz.rocket.mapper.OrderMapper;
import com.cz.rocket.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQTransactionListener
public class OrderTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("开始执行事务消息本地事务:{}", arg);
        if(!(arg instanceof Order)){
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        Order order = (Order) arg;
        SqlSession session = this.sqlSessionFactory.openSession(false);
        try{
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            mapper.insert(order);
            session.commit();
            return RocketMQLocalTransactionState.UNKNOWN;
        }catch (Exception e){
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        } finally {
            if(session != null){
                session.close();
            }
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        Object o = msg.getHeaders().get(OrderService.ORDER_ID);
        log.info("事务消息回查 参数 :{}  msg:{}", o, msg);
        Long id = null;
        if(o instanceof Long) {
            id = (Long)o;
        } else if (o instanceof String) {
            id = Long.parseLong(o.toString());
        }
        if(id != null){
            Order order = this.orderMapper.selectById(id);
            if(order != null && order.getAmount() != null){
                log.info("回查成功, orderId:{}", id);
                return RocketMQLocalTransactionState.COMMIT;
            }
            log.info("回查失败, orderId:{}", id);
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
