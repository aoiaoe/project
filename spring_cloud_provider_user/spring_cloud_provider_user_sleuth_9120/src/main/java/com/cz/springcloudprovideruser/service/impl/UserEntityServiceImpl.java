package com.cz.springcloudprovideruser.service.impl;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloud.entity.User;
import com.cz.springcloud.entity.UserEntityMix;
import com.cz.springcloudprovideruser.feign.EntityFeignClient;
import com.cz.springcloudprovideruser.holder.UserHolder;
import com.cz.springcloudprovideruser.service.UserEntityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserHolder userHolder;
    @Autowired
    private EntityFeignClient entityFeignClient;

    @Override
    public UserEntityMix geById(Long id) {
        Map<Long, User> holder = userHolder.getHolder();
        if (holder != null && !holder.isEmpty()) {
            User user = holder.get(id);
            if (user != null) {
                UserEntityMix res = new UserEntityMix();
                BeanUtils.copyProperties(user, res);
                List<Entity> all = this.entityFeignClient.getAll();
                res.setEntities(all);
                return res;
            }
        }
        return null;
    }
}
