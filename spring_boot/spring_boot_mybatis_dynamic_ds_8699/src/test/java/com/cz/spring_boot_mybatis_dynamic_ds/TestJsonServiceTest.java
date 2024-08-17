package com.cz.spring_boot_mybatis_dynamic_ds;

import com.cz.spring_boot_mybatis_dynamic_ds.entity.Book;
import com.cz.spring_boot_mybatis_dynamic_ds.entity.TestJson;
import com.cz.spring_boot_mybatis_dynamic_ds.service.TestJsonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jzm
 * @date 2024/6/17 : 10:58
 */
@SpringBootTest
public class TestJsonServiceTest {

    @Autowired
    private TestJsonService testJsonService;

    @Test
    public void test1(){
        TestJson testJson1 = new TestJson();
        Book b = new Book();
        b.setAuthor("东野圭吾");
        b.setDesc("外面方法2");
        b.setType("novel");
        b.setPrice(331);
        testJson1.setJsonString(b);

        TestJson testJson2 = new TestJson();
        Book b2 = new Book();
        b2.setAuthor("东野圭吾");
        b2.setDesc("内部方法2");
        b2.setType("novel");
        b2.setPrice(331);
        testJson2.setJsonString(b2);
        this.testJsonService.insertOut(testJson1, testJson2);
    }
}
