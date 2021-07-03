package com.cz.single_db.controller;


import com.cz.single_db.entity.Users;
import com.cz.single_db.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cz
 * @since 2020-10-10
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService iUsersService;

    @GetMapping
    public List<Users> list() {
        return this.iUsersService.listAll();
    }

    @PutMapping
    public boolean save(@RequestBody Users users) {
        return this.iUsersService.save(users);
    }

    @GetMapping(value = "/{id}")
    public Users get(@PathVariable("id") Integer id) {
        return this.iUsersService.getById(id);
    }

}
