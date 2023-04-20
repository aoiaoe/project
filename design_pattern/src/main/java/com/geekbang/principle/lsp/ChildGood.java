package com.geekbang.principle.lsp;

/**
 * @author jzm
 * @date 2023/4/18 : 17:34
 */
public class ChildGood extends Parent{

    @Override
    public void test(int num) {
        // 记录日志
        super.test(num);
    }
}
