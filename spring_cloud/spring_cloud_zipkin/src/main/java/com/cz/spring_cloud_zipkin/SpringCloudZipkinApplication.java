package com.cz.spring_cloud_zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
import zipkin2.server.internal.EnableZipkinServer;

import javax.sql.DataSource;

@SpringBootApplication
@EnableZipkinServer
public class SpringCloudZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZipkinApplication.class, args);
    }

    /**
     * zipkin存储为mysql时，需要
     *  注入事务控制器
     * @param dataSource
     * @return
     */
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }
}
