package com.cz.sharding_jdbc_master_slave.service.impl;

import com.cz.sharding_jdbc_master_slave.entity.Users;
import com.cz.sharding_jdbc_master_slave.mapper.UsersMapper;
import com.cz.sharding_jdbc_master_slave.service.IUserService;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users selectById(int id) {
        return this.usersMapper.selectById(id);
    }

    @Override
    public void save(Users user) {
        this.usersMapper.insert(user);
    }

    @Override
    public void save(int num) {
        for (int i = 0; i < num; i++) {
            Users user = new Users();
            user.setId(i);
            user.setName("user_" + i);
            this.usersMapper.insert(user);
        }
    }
}
