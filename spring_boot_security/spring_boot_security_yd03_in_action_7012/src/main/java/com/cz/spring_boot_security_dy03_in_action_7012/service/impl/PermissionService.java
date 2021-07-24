package com.cz.spring_boot_security_dy03_in_action_7012.service.impl;

import com.cz.spring_boot_security_dy03_in_action_7012.context.LoginUserContextHolder;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.LoginUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PermissionService {

    private static final String ALL_PERMISSION = "*:*:*";

    public boolean hasMenu(String menu){
        if(StringUtils.isEmpty(menu)){
            return true;
        }
        LoginUser user = LoginUserContextHolder.user();
        if(user == null || CollectionUtils.isEmpty(user.getAuthorities())){
            return false;
        }
        Set<String> menus = user.getAuthorities()
                .stream().map(e -> ((GrantedAuthority) e).getAuthority())
                .collect(Collectors.toSet());
        return hasPermissions(menus, menu);
    }

    public boolean hasAnyMenu(String...menu){
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION)
                || permissions.contains(permission);
    }
}
