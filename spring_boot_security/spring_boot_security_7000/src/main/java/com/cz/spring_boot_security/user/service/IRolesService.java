package com.cz.spring_boot_security.user.service;

import com.cz.spring_boot_security.user.entity.Roles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jzm
 * @since 2021-05-28
 */
public interface IRolesService{

    List<Roles> getByUserId(Integer userId);
}
