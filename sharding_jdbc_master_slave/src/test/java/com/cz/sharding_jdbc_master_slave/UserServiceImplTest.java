package com.cz.sharding_jdbc_master_slave;

import com.cz.sharding_jdbc_master_slave.entity.Order;
import com.cz.sharding_jdbc_master_slave.entity.Users;
import com.cz.sharding_jdbc_master_slave.mapper.OrderMapper;
import com.cz.sharding_jdbc_master_slave.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 读写分离
     */
    @Test
    public void testSave() {
        Users user = new Users();
        user.setId(2);
        user.setName("cz");
        this.userService.save(user);

    }

    /**
     * 读写分离 + 分表
     */
    @Test
    public void testSaveSplitTableBatch() {
        this.userService.save(8);

    }

    /**
     * 读写分离 + 分库分表分表
     */
    @Test
    public void testSaveSplitDbTableBatch() {
        this.userService.save(16);
        // 1  3  5  7  9   11  13  15

    }

    @Test
    public void testSelectById() {

        for (int i = 0; i < 10; i++) {
            Order oneOrder = this.orderMapper.findOneOrder();
            System.out.println(oneOrder);
        }
    }

    @Test
    public void insert(){
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setId(111111111L + i);
            order.setCompanyId(22222L);

            this.orderMapper.insert(order);
        }

    }
}
