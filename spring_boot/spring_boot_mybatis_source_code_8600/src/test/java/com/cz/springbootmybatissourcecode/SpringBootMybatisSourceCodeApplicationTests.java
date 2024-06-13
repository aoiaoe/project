package com.cz.springbootmybatissourcecode;

import com.cz.springbootmybatissourcecode.entity.Student;
import com.cz.springbootmybatissourcecode.entity.TestDeadlock;
import com.cz.springbootmybatissourcecode.service.StudentService;
import com.cz.springbootmybatissourcecode.service.TestDeadlockService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootMybatisSourceCodeApplicationTests {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TestDeadlockService testDeadlockService;

    @Test
    public void testInsert(){
        Student stu = new Student();
        stu.setName("金灶沐");
        stu.setAge(20);
        stu.setSex("男");
        System.out.println(this.studentService.insertStudent(stu));
    }

    @Test
    public void setTestDeadlockService() throws InterruptedException {
        new Thread(() -> {
            TestDeadlock deadlock = new TestDeadlock();
            deadlock.setId(7);
            deadlock.setC(7);
            deadlock.setD(7);
            testDeadlockService.test(6, deadlock);
        }, "A").start();
        new Thread(() -> {
            TestDeadlock deadlock = new TestDeadlock();
            deadlock.setId(8);
            deadlock.setC(8);
            deadlock.setD(8);
            testDeadlockService.test(6, deadlock);
        }, "B").start();

        TimeUnit.SECONDS.sleep(4);

    }

}
