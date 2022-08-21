package com.cz.springbootmybatissourcecode;

import com.cz.springbootmybatissourcecode.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;

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

}
