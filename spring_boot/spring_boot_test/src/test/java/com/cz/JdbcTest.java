package com.cz;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author jzm
 * @date 2022/7/28 : 14:58
 */
public class JdbcTest {


    @Test
    public void test1(){
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://106.53.96.154:3306/t?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&useSSL=false";
        String username = "nacos";
        String password = "123456";
        ResultSet rs = null;
        Statement stat = null;
        Connection conn = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, username, password);
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t where id =1");
            while (rs.next()){
                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                System.out.println(rs.getInt(3));
            }
            return;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
