package com.cz.spring_cloud_alibaba_sentinel.service;

import com.cz.spring_cloud_alibaba_api.domain.order.OrderVo;
import com.cz.spring_cloud_alibaba_sentinel.config.UserConfig;
import com.cz.spring_cloud_alibaba_sentinel.domain.UserVo;
import com.cz.spring_cloud_alibaba_sentinel.feign.OrderFeignClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

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
                    final List<OrderVo> orderVos = this.orderFeignClient.userOrders(id);
                    userVo.setOrders(orderVos);
                    return userVo;
                })
                .orElse(null);
    }
}
