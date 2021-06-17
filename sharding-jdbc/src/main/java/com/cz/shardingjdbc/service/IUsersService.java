package com.cz.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.shardingjdbc.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cz
 * @since 2020-10-05
 */
public interface IUsersService extends IService<User> {

    void processUsers(int i);

    List<User> getUsers();

    User selectUser(int i);
}
