package com.cz.springcloudprovider.holder;

import com.cz.springcloud.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alian
 * @date 2020/10/9 下午 3:55
 * @since JDK8
 */
public class EntityHolder {

    public static List<Entity> entityHolder = new ArrayList<>();

    static{
        for (int i = 0; i < 5; i++) {
            Entity entity = new Entity();
            entity.setId(i);
            entity.setMessage("entity_" + i);
            entityHolder.add(entity);
        }
    }
}
