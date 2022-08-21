package com.cz.spring_boot_security_dy03_in_action_7012.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.securitysdk.context.LoginUserContextHolder;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysUser;
import com.cz.spring_boot_security_dy03_in_action_7012.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private ISysUserService userService;

    @PreAuthorize("@permissionService.hasMenu('system:user:all')")
    @GetMapping(value = "/users")
    public List<SysUser> list(){
        return this.userService.list(new QueryWrapper<>());
    }

    @GetMapping
    public SysUser get(){
        return this.userService.getOne(new QueryWrapper<SysUser>().eq("user_id", LoginUserContextHolder.user().getId()));
    }
}
