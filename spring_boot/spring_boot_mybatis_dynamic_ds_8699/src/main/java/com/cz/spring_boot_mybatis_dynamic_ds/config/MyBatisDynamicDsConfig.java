package com.cz.spring_boot_mybatis_dynamic_ds.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import lombok.AllArgsConstructor;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author jzm
 * @date 2024/6/17 : 18:28
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MyBatisDynamicDsConfig {

    private final MybatisPlusProperties properties;

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    List<DataSource> dataSources;
    @PostConstruct
    public void init(){
        // 奇葩，我为啥要将分库数据源加入到动态数据源？
        DynamicRoutingDataSource bean = applicationContext.getBean(DynamicRoutingDataSource.class);
        bean.addDataSource("shard", applicationContext.getBean(ShardingDataSource.class));
        System.out.println();
    }

}
