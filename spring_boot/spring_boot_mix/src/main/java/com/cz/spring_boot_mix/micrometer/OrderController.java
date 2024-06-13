package com.cz.spring_boot_mix.micrometer;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 指标测试端点
 */
@Slf4j
@RequestMapping(value = "order")
@RestController
public class OrderController {

    private LongAdder longAdder = new LongAdder();

    @PostConstruct
    public void inti(){
        //
        Metrics.gauge("totalSuccess", longAdder.sum());
    }

    @GetMapping(value = "/createOrder")
    public void createOrder(Long userId, Long tenantId){
        // 请求数
        Metrics.counter("reqRcv").increment();
        Instant now = Instant.now();
        try {
            // 模拟数据处理200毫秒
            TimeUnit.MILLISECONDS.sleep(200);
            boolean status = getTenantStatus(tenantId);
            if(!status){
                throw new IllegalStateException("商户已闭店");
            }
            Metrics.timer("orderSuccess")
                    .record(Duration.between(now, Instant.now()));
            log.info("下单成功");
        } catch (Exception e){
            if(StringUtil.isNullOrEmpty(e.getMessage())){
                e = new RuntimeException("系统错误" + e.getClass(), e);
            }
            log.error("下单失败:userId{}, error:{}", userId, e);
            Metrics.timer("orderFail", "reason", e.getMessage())
                    .record(Duration.between(now, Instant.now()));
        }
    }

    private boolean getTenantStatus(Long tenantId) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        return tenantId == 1;
    }


}
