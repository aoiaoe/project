package com.cz.spring_boot_security_dy03_in_action_7012.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAuthority implements GrantedAuthority {

    private String permission;

    @Override
    public String getAuthority() {
        return permission;
    }
}
