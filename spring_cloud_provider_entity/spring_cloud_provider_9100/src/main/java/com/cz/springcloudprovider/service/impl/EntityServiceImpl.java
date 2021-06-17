package com.cz.springcloudprovider.service.impl;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloudprovider.holder.EntityHolder;
import com.cz.springcloudprovider.service.IEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/9 下午 3:52
 * @since JDK8
 */
@Slf4j
@Service
public class EntityServiceImpl implements IEntityService {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public Entity getById(Integer id) {
        if (id < 0 || id > 4) {
            return null;
        }
        Entity entity = EntityHolder.entityHolder.get(id);
        if (entity != null) {
            entity.setExtra(serverPort);
        }
        return entity;
    }

    @Override
    public List<Entity> getAll() {
        List<Entity> entityHolder = EntityHolder.entityHolder;
        for (Entity entity : entityHolder) {
            entity.setExtra(serverPort);
        }
        return entityHolder;
    }
}
