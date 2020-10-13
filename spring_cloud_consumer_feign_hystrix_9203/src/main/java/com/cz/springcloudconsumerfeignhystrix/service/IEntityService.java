package com.cz.springcloudconsumerfeignhystrix.service;

import com.cz.springcloud.entity.Entity;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/13 下午 4:38
 * @since JDK8
 */
public interface IEntityService {

    List<Entity> getAll();

    Entity getById(Integer id);
}
