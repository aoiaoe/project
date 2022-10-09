package com.cz.spring_cloud_alibaba.service.impl;

import com.cz.spring_cloud_alibaba.mapper.UsersMapper;
import com.cz.spring_cloud_alibaba.mapper.entity.Users;
import com.cz.spring_cloud_alibaba.service.IUserService;
import com.cz.spring_cloud_alibaba.domain.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author jzm
 * @date 2022/10/9 : 14:17
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private static final Users EMPTY = new Users();
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserVo user(Integer id) {
        Users user = this.usersMapper.selectById(id);
        user = Optional.ofNullable(user).orElse(EMPTY);
        return new UserVo(user.getId(), user.getName(), user.getPassword());
    }
}
