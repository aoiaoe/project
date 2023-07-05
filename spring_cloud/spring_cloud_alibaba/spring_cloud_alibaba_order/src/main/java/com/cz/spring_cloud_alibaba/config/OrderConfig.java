package com.cz.spring_cloud_alibaba.config;

import com.cz.spring_cloud_alibaba.domain.OrderDto;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "test")
public class OrderConfig {

    private List<OrderDto> orders;

    @Value("${configPriority:default value}")
    private String configPriority;

    @PostConstruct
    public void inti(){
        log.info("[解析订单数据]");
        log.info("[测试nacos配置文件优先级]:{}", configPriority);
//        Optional.ofNullable(orders).orElse(new ArrayList<>())
//                .stream()
//                .forEach(e -> {
//                    try {
//                        final OrderDto orderDto = JSON.parseObject(e, OrderDto.class);
//                        OrderHolder.orders.add(orderDto);
//                    }catch (Exception ee) {
//                        log.error("[解析订单数据出错]", e);
//                    }
//                });
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new HikariDataSource();
    }
}
