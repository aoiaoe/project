package com.cz;

import lombok.Data;

/**
 * @author jzm
 * @date 2022/9/22 : 17:40
 */
@Data
public class Demo {

    private Long id;

    private String name;

    public static void main(String[] args) {
        int x = 1, y = 2;
        int z = 3;
        z = y = x;
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }
}
