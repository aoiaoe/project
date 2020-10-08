package com.cz.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.shardingjdbc.entity.User;
import com.cz.shardingjdbc.mapper.UsersMapper;
import com.cz.shardingjdbc.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User> implements IUsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processUsers(int i) {
        insertUsers(i);
    }

    private List<Long> insertUsers(int num) {
        List<Long> result = new ArrayList<>(10);
        for (Long i = 1L; i <= num; i++) {
            User user = new User();
            user.setUserId(i);
            user.setUserName("user_" + i);
            usersMapper.insert(user);
            result.add(user.getUserId());
            System.out.println("Insert User:" + user.getUserId());
        }
        return result;
    }

    @Override
    public List<User> getUsers()  {
        return usersMapper.selectAll();
    }

    @Override
    public User selectUser(int i) {
        return usersMapper.selectById(i);
    }
}
