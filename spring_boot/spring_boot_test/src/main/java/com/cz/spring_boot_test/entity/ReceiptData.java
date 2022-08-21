package com.cz.spring_boot_test.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author alian
 * @date 2021/1/22 上午 9:31
 * @since JDK8
 */
@ToString
@Data
public class ReceiptData {

    private Long id;

    private String name;

    private Demo demoA;

    private Demo demoB;

}
