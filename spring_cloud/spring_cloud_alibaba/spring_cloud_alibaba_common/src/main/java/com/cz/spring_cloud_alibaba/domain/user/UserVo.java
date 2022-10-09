package com.cz.spring_cloud_alibaba.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jzm
 * @date 2022/10/9 : 11:18
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVo {

    private Integer id;

    private String name;

    private String password;
}
