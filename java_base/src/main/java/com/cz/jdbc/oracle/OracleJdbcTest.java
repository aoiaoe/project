package com.cz.jdbc.oracle;

import java.sql.*;
import java.util.Properties;

/**
 * @author jzm
 * @date 2022/11/3 : 12:44
 */
public class OracleJdbcTest {

    public static void main(String[] args) {
        //首先定义下连接数据的URL、用户名、密码
        String url="jdbc:oracle:thin:@tx-sh:1521:xe";
        String user="sephiroth";
        String password="123456";

        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            //1.加载驱动，使用了反射的知识
            Class.forName("oracle.jdbc.driver.OracleDriver");
            /*
             *  使用DriverManager获取数据库连接
             *  其中返回的Connection就代表了JAVA程序和数据库的连接
             *  不同数据库的URL写法需要查看驱动文档知道，用户名、密码由DBA分配
             */
            Properties pro = new Properties();
            pro.setProperty("user", user);
            pro.setProperty("password", password);
            pro.put("remarksReporting", "true");// 注意这里不可或缺
            conn = DriverManager.getConnection(url, pro);
            //使用Connection来创建一个Statement对象
            stmt = conn.createStatement();
            //执行SQL语句
            /*
             * Statement有三种执行SQL语句的方法
             * 1、execute可执行任何SQL语句    --返回一个boolean值
             * 如果执行后，第一个结果是ResultSet,则返回true，否则返回false
             * 2、executeQuery 执行select 语句    --返回查询到的结果集
             * 3、executeUpdate用于执行DML语句。---返回一个整数，代表被SQL语句影响的记录数
             */
            String sql="select e.* from sephiroth.users e";
            rs = stmt.executeQuery(sql);
            //ResultSet有系列的GetXXX(索引名||列名),用于获取记录指针指向行、特定列的值
            //不断的使用next将记录指针下移一行，如果依然指向有效行，则指针指向有效行的记录
            while(rs.next()){
                //使用索引
                System.out.println("员工号："+rs.getInt(1)+"	员工姓名："+rs.getString(2));
            }

            DatabaseMetaData metaData = conn.getMetaData();

            rs = metaData.getColumns(null, null, "USERS", null);
            while (rs.next()){
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("TYPE_NAME");
                String columnSize = rs.getString("COLUMN_SIZE");
                String remarks = rs.getString("REMARKS");
                boolean nullable = rs.getBoolean("NULLABLE");
                System.out.println(String.format("列名:%s, 类型:%s 长度:%s 可否为空：%s 注释:%s", columnName, columnType, columnSize, nullable, remarks));
            }
            rs = metaData.getPrimaryKeys(null, null, "USERS");
            while (rs.next()) {
                String pkName = rs.getString("COLUMN_NAME");
                System.out.println("主键名称 ：" + pkName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }


    }
}
