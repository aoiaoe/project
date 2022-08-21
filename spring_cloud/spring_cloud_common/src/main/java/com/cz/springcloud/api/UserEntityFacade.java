package com.cz.springcloud.api;

import com.cz.springcloud.entity.UserEntityMix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/userEntity")
public interface UserEntityFacade {

    @GetMapping(value = "/{id}")
    UserEntityMix geById(@PathVariable(value = "id") Long id);
}
