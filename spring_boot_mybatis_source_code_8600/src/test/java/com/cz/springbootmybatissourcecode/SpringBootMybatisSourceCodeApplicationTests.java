package com.cz.springbootmybatissourcecode;

import com.cz.springbootmybatissourcecode.entity.Student;
import com.cz.springbootmybatissourcecode.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootMybatisSourceCodeApplicationTests {

    @Autowired
    private StudentService studentService;

    @Test
    public void testInsert(){
        Student stu = new Student();
        stu.setName("金灶沐");
        stu.setAge(20);
        stu.setSex("男");
        System.out.println(this.studentService.insertStudent(stu));
    }

}
