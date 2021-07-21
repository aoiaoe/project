package com.cz.spring_boot_security_dy03_in_action_7012.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Builder
@Data
public class LoginUser implements UserDetails {

    private String userName;
    private String password;
    private String status;
    private Set<CustomerAuthority> permissions;



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
        return "1".equals(status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return "1".equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return "1".equals(status);
    }

    @Override
    public boolean isEnabled() {
        return "1".equals(status);
    }
}
