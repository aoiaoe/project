package com.cz.springbootes.entity;

import lombok.Data;

/**
 * @author jzm
 * @date 2022/4/14 : 17:46
 */
@Data
public class Page {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
