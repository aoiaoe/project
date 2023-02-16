package com.cz.spring_cloud_alibaba.service.impl;

import com.cz.spring_cloud_alibaba.mapper.UsersMapper;
import com.cz.spring_cloud_alibaba.mapper.entity.Users;
import com.cz.spring_cloud_alibaba.service.IUserService;
import com.cz.spring_cloud_alibaba.domain.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author jzm
 * @date 2022/10/9 : 14:17
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private static final Users EMPTY = new Users();
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public UserVo user(Integer id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-config-sentinel-order");
        Optional.ofNullable(instances.get(0).getMetadata()).orElse(new HashMap<>())
                .entrySet()
                .forEach(e -> {
                   log.info("元数据 key:{}, value:{}", e.getKey(), e.getValue());
                });
        Users user = this.usersMapper.selectById(id);
        user = Optional.ofNullable(user).orElse(EMPTY);
        return new UserVo(user.getId(), user.getName(), user.getPassword());
    }
}
