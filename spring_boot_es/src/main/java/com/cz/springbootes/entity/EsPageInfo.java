package com.cz.springbootes.entity;

import lombok.Data;

import java.util.List;

/**
 * @author jzm
 * @date 2022/4/14 : 17:45
 */
@Data
public class EsPageInfo<T> extends Page {

    private Integer total;

    private Integer pages;

    private List<T> list;
}
