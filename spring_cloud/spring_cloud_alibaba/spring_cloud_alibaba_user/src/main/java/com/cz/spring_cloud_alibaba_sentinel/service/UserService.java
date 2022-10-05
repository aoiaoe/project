package com.cz.spring_cloud_alibaba_sentinel.service;

import com.cz.spring_cloud_alibaba_api.domain.order.OrderVo;
import com.cz.spring_cloud_alibaba_sentinel.config.UserConfig;
import com.cz.spring_cloud_alibaba_sentinel.domain.UserVo;
import com.cz.spring_cloud_alibaba_sentinel.feign.OrderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RefreshScope
@Slf4j
@Service
public class UserService {

    private String testKey;

    @Value("${testKey}")
    public void setTestKey(String testKey){
        this.testKey = testKey;
        log.info("[设置测试值] :{}", testKey);
    }

    @Autowired
    private UserConfig userConfig;
    @Autowired
    private OrderFeignClient orderFeignClient;

    public UserVo user(Long id){

        return Optional.ofNullable(userConfig.getUsers()).orElse(new ArrayList<>())
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    final UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(e, userVo);
                    log.info("testKey:{}", testKey);
                    final List<OrderVo> orderVos = this.orderFeignClient.userOrders(id);
                    userVo.setOrders(orderVos);
                    return userVo;
                })
                .orElse(null);
    }
}
