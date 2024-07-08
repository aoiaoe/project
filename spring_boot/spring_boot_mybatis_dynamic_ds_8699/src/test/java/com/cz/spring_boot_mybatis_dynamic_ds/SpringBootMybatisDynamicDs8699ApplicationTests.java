package com.cz.spring_boot_mybatis_dynamic_ds;

import com.cz.spring_boot_mybatis_dynamic_ds.entity.Book;
import com.cz.spring_boot_mybatis_dynamic_ds.entity.TestJson;
import com.cz.spring_boot_mybatis_dynamic_ds.mapper.TestJsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMybatisDynamicDs8699ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private TestJsonMapper testJsonMapper;

    @Test
    public void testSelectJson(){
        TestJson testJson = this.testJsonMapper.selectById11(1);
        System.out.println(testJson);
        testJson = this.testJsonMapper.selectById(1);
        System.out.println(testJson);
    }

    @Test
    public void insert(){
        TestJson testJson = new TestJson();
        Book b = new Book();
        b.setAuthor("东野圭吾");
        b.setDesc("东野圭吾的小说");
        b.setType("novel");
        b.setPrice(331);
        testJson.setJsonString(b);
        this.testJsonMapper.insert(testJson);
    }
}
