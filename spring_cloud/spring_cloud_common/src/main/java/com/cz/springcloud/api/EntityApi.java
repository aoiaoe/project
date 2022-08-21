package com.cz.springcloud.api;

import com.cz.springcloud.entity.Entity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/9 下午 3:41
 * @since JDK8
 */
@RequestMapping(value = "/entity")
public interface EntityApi {

    @GetMapping(value = "/list")
    List<Entity> getAll();

    @GetMapping(value = "/{id}")
    Entity getById(@PathVariable(value = "id") Integer id);
}
