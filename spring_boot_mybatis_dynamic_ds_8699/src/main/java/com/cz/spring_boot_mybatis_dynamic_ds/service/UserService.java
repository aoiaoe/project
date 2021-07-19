package com.cz.spring_boot_mybatis_dynamic_ds.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring_boot_mybatis_dynamic_ds.entity.Users;
import com.cz.spring_boot_mybatis_dynamic_ds.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, Users> {

    @DS("#ds")
    public Users selectUserById(Integer id, String ds){
        if (id == null || id < 0){
            return null;
        }
        return this.baseMapper.selectOne(new QueryWrapper<Users>().eq("id", id));
    }
}
