package com.cz.spring_boot_mybatis_dynamic_ds.contoller;

import com.cz.spring_boot_mybatis_dynamic_ds.entity.Users;
import com.cz.spring_boot_mybatis_dynamic_ds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{ds}/{id}")
    public Users getUserById(@PathVariable(value = "ds") String ds,
                             @PathVariable(value = "id") Integer id){
        return this.userService.selectUserById(id,ds);
    }

}
