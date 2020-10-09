package com.cz.springcloud.api;

import com.cz.springcloud.entity.Entity;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/9 下午 3:41
 * @since JDK8
 */
public interface EntityApi {

    List<Entity> getAll();

    Entity getById(Integer id);
}
