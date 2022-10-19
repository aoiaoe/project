package com.cz.spring_cloud_alibaba.controller;

import com.cz.spring_cloud_alibaba.domain.UserDto;
import com.cz.spring_cloud_alibaba.domain.UserVo;
import com.cz.spring_cloud_alibaba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 内部使用openfeign进行远程通信
     * @param id
     * @return
     */
    @GetMapping(value = "/withOrder")
    public UserVo user(Long id){
        return this.userService.user(id);
    }


    @GetMapping(value = "/{id}")
    public UserVo usersPath(@PathVariable Long id){
        return this.userService.userInfo(id);
    }

    @GetMapping
    public UserVo users(Long id){
        return this.userService.userInfo(id);
    }

    /**
     * 内部是用ribbon进行远程通信
     * @param id
     * @return
     */
    @GetMapping(value = "/ribbon/{id}")
    public UserVo userRibbon(@PathVariable Long id){
        return this.userService.userRibbonInfo(id);
    }

    @PostMapping(value = "/add")
    public boolean createUser(@RequestBody UserDto param){
        return this.userService.addUser(param);
    }
}
