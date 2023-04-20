package com.geekbang.principle.lsp;

/**
 * 不符合里式替换原则，因为改变了父类的逻辑
 * @author jzm
 * @date 2023/4/18 : 17:32
 */
public class ChildBad extends Parent{

    @Override
    public void test(int num) {
        if(num < 0){
            throw new RuntimeException("param can't be negative integer!");
        }
        super.test(num);
    }
}
