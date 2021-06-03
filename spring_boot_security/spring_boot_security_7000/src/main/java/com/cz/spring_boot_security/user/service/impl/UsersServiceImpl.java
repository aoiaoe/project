package com.cz.spring_boot_security.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cz.spring_boot_security.user.entity.Roles;
import com.cz.spring_boot_security.user.entity.Users;
import com.cz.spring_boot_security.user.mapper.UsersMapper;
import com.cz.spring_boot_security.user.service.IRolesService;
import com.cz.spring_boot_security.user.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jzm
 * @since 2021-05-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService, UserDetailsService {

    @Autowired
    private IRolesService rolesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.getOne(new LambdaQueryWrapper<Users>().eq(Users::getName, username));
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Roles> roles = this.rolesService.getByUserId(user.getId());
        user.setRolesList(roles);
        return user;
    }
}
