package com.cz.springbootes.entity;

import lombok.Data;

/**
 * @author jzm
 * @date 2022/4/14 : 17:50
 */
@Data
public class HighLight<T> extends Id<T> {

    private String highlight;
}
