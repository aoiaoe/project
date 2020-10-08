package com.cz.sharding_jdbc_master_slave.service;

import com.cz.sharding_jdbc_master_slave.entity.Users;

public interface IUserService {

    Users selectById(int id);


    void save(Users user);

    void save(int i);
}
