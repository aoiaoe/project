package com.cz.springbootredis.service;

import com.cz.springbootredis.entity.CacheObj;

import java.util.List;

public interface CacheService {

    List<CacheObj> getList();

    CacheObj getCache(Long id);

    boolean addObj(Long id);

    CacheObj updateObj(Long id);
}
