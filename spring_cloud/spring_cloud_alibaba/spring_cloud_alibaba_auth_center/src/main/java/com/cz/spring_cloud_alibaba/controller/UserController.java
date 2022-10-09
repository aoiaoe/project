package com.cz.spring_cloud_alibaba.controller;

import com.cz.spring_cloud_alibaba.domain.user.UserVo;
import com.cz.spring_cloud_alibaba.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/10/9 : 11:16
 */
@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public UserVo user(@PathVariable Integer id){
        return this.userService.user(id);
    }
}
