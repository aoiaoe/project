package com.cz.shardingjdbc;

import com.cz.shardingjdbc.entity.User;
import com.cz.shardingjdbc.service.IHealthRecordService;
import com.cz.shardingjdbc.service.IUsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class HealthRecordServiceImplTest {

    @Autowired
    private IUsersService usersService;
    @Autowired
    private IHealthRecordService healthRecordService;

    @Test
    public void testSaveRecord() {
        this.healthRecordService.processHealthRecords();
    }

    @Test
    public void testProcessUsers() throws Exception {
        usersService.processUsers(18);
    }

    @Test
    public void testUsers() throws Exception {
        List<User> users = usersService.getUsers();
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testSelect() {
        User user = this.usersService.selectUser(2);
        System.out.println(user);
    }
}
