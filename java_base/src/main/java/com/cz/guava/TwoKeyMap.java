package com.cz.guava;

import com.google.common.collect.HashBasedTable;

/**
 * @author jzm
 * @date 2023/2/23 : 15:59
 */
public class TwoKeyMap {

    public static void main(String[] args) {
        HashBasedTable<Object, Object, Object> testTable = HashBasedTable.create();
        testTable.put("cz", "jzm", 20);
        testTable.put("wxf", "fwe", 20);
        testTable.put("cz", "cz", 22);
        System.out.println(testTable.row("cz").keySet());
        System.out.println(testTable.row("cz").values());
    }
}
