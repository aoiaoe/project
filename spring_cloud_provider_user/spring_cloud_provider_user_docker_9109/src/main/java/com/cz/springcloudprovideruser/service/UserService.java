package com.cz.springcloudprovideruser.service;

import com.cz.springcloud.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User get(Long id);
}
