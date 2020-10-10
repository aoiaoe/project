package com.cz.elk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.elk.entity.Users;
import com.cz.elk.mapper.UsersMapper;
import com.cz.elk.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cz
 * @since 2020-10-10
 */
@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public List<Users> listAll() {
        log.info("get all users");
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public void saveUserBatch() {
        final Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Users user = new Users();
            user.setId(i);
            user.setName("user_" + i);
            user.setAge(random.nextInt(10) + 20);
            user.setSex(user.getAge() % 2 == 0 ? "男" : "女");
            this.baseMapper.insert(user);
        }
    }

}
