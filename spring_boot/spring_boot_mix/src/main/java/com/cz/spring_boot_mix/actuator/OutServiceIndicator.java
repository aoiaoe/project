package com.cz.spring_boot_mix.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义外部依赖服务的健康检测器
 * 将此类定义成组件，会自动注入actuator中，通过health端点可以查看当前状态,
 *      前提是需要配置 management.endpoint.health.show-details: always
 * 如果此类检测结果为Down，则health的结果也会是Down
 */
@Slf4j
@Component
public class OutServiceIndicator implements HealthIndicator {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Health health() {
        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity("http://127.0.0.1:11112/test", String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Health.up().withDetail("Out Service", "Remote call successed").build();
            }
        }catch (Exception e){
            log.error("外部服务健康检测失败:{}", e);
        }
        return Health.down().withDetail("Out Service", "Remote call failed").build();
    }
}
