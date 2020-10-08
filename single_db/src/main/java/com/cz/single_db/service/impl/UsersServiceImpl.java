package com.cz.single_db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.single_db.entity.Users;
import com.cz.single_db.mapper.UsersMapper;
import com.cz.single_db.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public void processUsers() {
        insertUsers();
    }

    private List<Integer> insertUsers() {
        List<Integer> result = new ArrayList<>(10);
        for (Long i = 1L; i <= 10; i++) {
            Users user = new Users();
            user.setId(i.intValue());
            user.setName("user_" + i);
            usersMapper.insert(user);
            result.add(user.getId());
            System.out.println("Insert User:" + user.getId());
        }
        return result;
    }

    @Override
    public List<Users> getUsers()  {
        return usersMapper.selectAll();
    }
}
