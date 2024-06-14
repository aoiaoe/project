package com.cz.spring_boot_mybatis_dynamic_ds.entity;

import lombok.Data;

/**
 * @author jzm
 * @date 2024/5/23 : 16:46
 */
@Data
public class Book {

    private String type;
    private String desc;
    private int price;
    private String author;
    private String publish_date;

}
