package com.cz.springcloudconsumerfeignhystrixstream.service;

import com.cz.springcloud.entity.Entity;

public interface EntitySerivce {

    Entity getById(Integer id);
}
