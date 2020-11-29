package com.cz.springcloudconsumerfeignhystrixstream.service;

import com.cz.springcloud.entity.User;

public interface UserService {
    User getById(Long id);
}
