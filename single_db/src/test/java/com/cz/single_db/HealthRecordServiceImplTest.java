package com.cz.single_db;

import com.cz.single_db.service.IUsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class HealthRecordServiceImplTest {

    @Autowired
    private IUsersService usersService;


    @Test
    public void testProcessUsers() throws Exception {
        usersService.saveUserBatch();
    }
}
