package com.cz.spring_boot_security_dy03_in_action_7012.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.cz.spring_boot_security_dy03_in_action_7012.constants.Constants;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.CustomerAuthority;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.LoginUser;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysRole;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.SysUser;
import com.cz.spring_boot_security_dy03_in_action_7012.constants.UserStatus;
import com.cz.spring_boot_security_dy03_in_action_7012.mapper.SysUserMapper;
import com.cz.spring_boot_security_dy03_in_action_7012.service.ISysRoleService;
import com.cz.spring_boot_security_dy03_in_action_7012.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", userName));
        if(user == null){
            throw new UsernameNotFoundException("账号或者密码不正确");
        }
        if(UserStatus.DISABLE.getCode().equals(user.getStatus()) || UserStatus.DELETED.getCode().equals(user.getDelFlag())){
            throw new RuntimeException("账号已停用/删除");
        }

        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getUserId());
        loginUser.setUserName(user.getUserName());
        loginUser.setPassword(user.getPassword());
        loginUser.setStatus(user.getStatus());
        loginUser.setPermissions(createAuthorities(user));
        return loginUser;
    }

    private Set<CustomerAuthority> createAuthorities(SysUser user){
        // 判断是否是管理员角色 管理员角色获取所有权限
        List<SysRole> roles = sysRoleService.getRolesByUserId(user.getUserId());
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }
        boolean isAdmin = roles.stream().anyMatch(e -> Constants.ROLE_ADMIN.equals(e.getRoleKey()));
        Set<CustomerAuthority> authorities = new HashSet<>();
        if(isAdmin){
            // 管理员有所有权限
            authorities.add(new CustomerAuthority("*:*:*"));
        } else {
            List<String> allPerms = baseMapper.getAllByUserId(user.getUserId());
            if (CollectionUtils.isEmpty(allPerms)) {
                return null;
            }
            for (String perm : allPerms) {
                String[] split = perm.split(",");
                for (String s : split) {
                    authorities.add(new CustomerAuthority(s));
                }
            }
        }
        return authorities;
    }
}
