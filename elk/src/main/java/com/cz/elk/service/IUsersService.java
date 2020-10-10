package com.cz.elk.service;

import com.cz.elk.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cz
 * @since 2020-10-10
 */
public interface IUsersService extends IService<Users> {

    List<Users> listAll();

    void saveUserBatch();
}
