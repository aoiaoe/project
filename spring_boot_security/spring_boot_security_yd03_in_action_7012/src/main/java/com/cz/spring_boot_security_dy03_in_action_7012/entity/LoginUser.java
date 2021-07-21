package com.cz.spring_boot_security_dy03_in_action_7012.entity;

import com.cz.spring_boot_security_dy03_in_action_7012.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails, Serializable {

    private Long id;
    private String userName;
    private String password;
    private String status;
    private Set<CustomerAuthority> permissions;

    // 存储在redis中的唯一标识, 非jwt token
    private String token;


    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserStatus.NORMAL.getCode().equals(status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserStatus.NORMAL.getCode().equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserStatus.NORMAL.getCode().equals(status);
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.NORMAL.getCode().equals(status);
    }
}
