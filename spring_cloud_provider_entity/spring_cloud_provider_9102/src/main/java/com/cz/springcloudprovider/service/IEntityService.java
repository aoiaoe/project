package com.cz.springcloudprovider.service;

import com.cz.springcloud.entity.Entity;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/9 下午 3:46
 * @since JDK8
 */
public interface IEntityService {


    Entity getById(Integer id);

    List<Entity> getAll();
}
