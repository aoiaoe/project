package com.cz.springcloudconsumerfeign.controller;

import com.cz.springcloud.entity.User;
import com.cz.springcloudconsumerfeign.feign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alian
 * @date 2020/11/12 下午 4:31
 * @since JDK8
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserClient userClient;

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable(value = "id") Long id) {
        return userClient.get(id);
    }

}
