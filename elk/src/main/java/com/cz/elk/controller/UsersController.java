package com.cz.elk.controller;


import com.cz.elk.entity.Users;
import com.cz.elk.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cz
 * @since 2020-10-10
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService iUsersService;

    @GetMapping
    public List<Users> list(){
        return this.iUsersService.listAll();
    }

    @PutMapping
    public boolean save(@RequestBody Users users){
        log.info("save user param:{}", users);
        return this.iUsersService.save(users);
    }

    @GetMapping(value = "/{id}")
    public Users get(@PathVariable("id") Integer id){
        Users user =  this.iUsersService.getById(id);
        log.info("get user param:{}, result:{}", id, user);
        return user;
    }

    @PostMapping
    public boolean saveBatch(){
        return this.iUsersService.saveUserBatch();
    }


}
