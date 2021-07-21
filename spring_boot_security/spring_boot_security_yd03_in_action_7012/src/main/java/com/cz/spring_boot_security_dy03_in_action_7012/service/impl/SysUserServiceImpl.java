package com.cz.spring_boot_security_dy03_in_action_7012.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.CustomerAuthority;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.LoginUser;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysMenu;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysUser;
import com.cz.spring_boot_security_dy03_in_action_7012.enums.UserStatus;
import com.cz.spring_boot_security_dy03_in_action_7012.mapper.SysUserMapper;
import com.cz.spring_boot_security_dy03_in_action_7012.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author cz
 * @since 2021-07-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("userName", userName));
        if(user == null){
            throw new UsernameNotFoundException("账号或者密码不正确");
        }
        if(UserStatus.DISABLE.getCode().equals(user.getStatus()) || UserStatus.DELETED.getCode().equals(user.getDelFlag())){
            throw new RuntimeException("账号已停用/删除");
        }

        return LoginUser.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .status(user.getStatus())
                .permissions(createAuthorities(user))
                .build();
    }

    private Set<CustomerAuthority> createAuthorities(SysUser user){
        // TODO 判断是否是管理员角色 管理员角色获取所有权限
//        if(user.)
        List<String> allPerms = baseMapper.getAllByUserId(user.getUserId());
        if(CollectionUtils.isEmpty(allPerms)){
            return null;
        }
        Set<CustomerAuthority> authorities = new HashSet<>();
        for (String perm : allPerms) {
            String[] split = perm.split(",");
            for (String s : split) {
                authorities.add(new CustomerAuthority(s));
            }
        }
        return authorities;
    }
}
