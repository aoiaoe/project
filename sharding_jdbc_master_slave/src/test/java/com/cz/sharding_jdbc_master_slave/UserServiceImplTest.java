package com.cz.sharding_jdbc_master_slave;

import com.cz.sharding_jdbc_master_slave.entity.Users;
import com.cz.sharding_jdbc_master_slave.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    /**
     * 读写分离
     */
    @Test
    public void testSave(){
        Users user = new Users();
        user.setId(2);
        user.setName("cz");
        this.userService.save(user);

    }

    /**
     * 读写分离 + 分表
     */
    @Test
    public void testSaveSplitTableBatch(){
        this.userService.save(8);

    }

    /**
     * 读写分离 + 分库分表分表
     */
    @Test
    public void testSaveSplitDbTableBatch(){
        this.userService.save(16);
        // 1  3  5  7  9   11  13  15

    }

    @Test
    public void testSelectById(){
        Users users = this.userService.selectById(5);
        System.out.println(users);
    }
}
