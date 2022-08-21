package com.cz.springcloudprovideruser.controller;

import com.cz.springcloud.api.UserEntityFacade;
import com.cz.springcloud.entity.UserEntityMix;
import com.cz.springcloudprovideruser.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserEntityController implements UserEntityFacade {

    @Autowired
    private UserEntityService userEntityService;

    @Override
    public UserEntityMix geById(@PathVariable(value = "id") Long id) {
        return this.userEntityService.geById(id);
    }
}
