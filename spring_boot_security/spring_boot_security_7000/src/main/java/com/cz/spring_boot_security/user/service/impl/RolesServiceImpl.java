package com.cz.spring_boot_security.user.service.impl;

import com.cz.spring_boot_security.user.entity.Roles;
import com.cz.spring_boot_security.user.mapper.RolesMapper;
import com.cz.spring_boot_security.user.service.IRolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jzm
 * @since 2021-05-28
 */
@Service
public class RolesServiceImpl implements IRolesService {

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public List<Roles> getByUserId(Integer userId) {
        return this.rolesMapper.selectByUserId(userId);
    }
}
