package com.cz.springbootmybatissourcecode;

import com.cz.springbootmybatissourcecode.entity.Student;
import com.cz.springbootmybatissourcecode.entity.TestDeadlock;
import com.cz.springbootmybatissourcecode.mapper.TestDeadLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OriginMybatisDemoTest {

    private SqlSessionFactory sessionFactory;
    private SqlSession session;

    @Before
    public void setUp(){
        String resource = "mybatis-config/MybatisConfiguration.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @After
    public void close(){
        session.close();
    }

    @Test
    public void testInsert(){
        Student stu = new Student();
        stu.setName("金灶沐6");
        stu.setAge(206);
        stu.setSex("男6");
        int insert = session.insert("com.cz.springbootmybatissourcecode.mapper.StudentMapper.insertStudent", stu);
        System.out.println(insert);
        session.commit();
    }

    @Test
    public void testDeadLock() throws InterruptedException {
        TestDeadLock mapper1 = session.getMapper(TestDeadLock.class);
        TestDeadLock mapper2 = session.getMapper(TestDeadLock.class);
        new Thread(() -> {
            TestDeadlock testDeadlock = mapper1.selectForUpdate(6);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TestDeadlock deadlock = new TestDeadlock();
            deadlock.setId(7);
            deadlock.setC(7);
            deadlock.setD(7);
            mapper1.insertTestDeadLock(deadlock);
            log.info("完成插入7");
        }, "A").start();
        new Thread(() -> {
            TestDeadlock testDeadlock = mapper2.selectForUpdate(6);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TestDeadlock deadlock = new TestDeadlock();
            deadlock.setId(8);
            deadlock.setC(8);
            deadlock.setD(8);
            mapper2.insertTestDeadLock(deadlock);
            log.info("完成插入8");
        }, "B").start();

        TimeUnit.MILLISECONDS.sleep(5);
    }
}
