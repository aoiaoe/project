package com.cz.springcloud.api;

import com.cz.springcloud.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/user")
public interface UserApi {

    @GetMapping(value = "/list")
    List<User> findAll();

    @GetMapping(value = "/{id}")
    User get(@PathVariable("id") Long id);

}
