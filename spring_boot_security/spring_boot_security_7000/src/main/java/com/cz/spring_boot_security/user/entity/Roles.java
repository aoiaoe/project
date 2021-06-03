package com.cz.spring_boot_security.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jzm
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode()
public class Roles{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String roleName;


}
