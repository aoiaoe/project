package com.geekbang.principle.lsp;

/**
 * @author jzm
 * @date 2023/4/18 : 17:32
 */
public class Demo {

    public void testLiskov(Parent parent, int num){
        parent.test(num);
    }


    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.testLiskov(new Parent(), -1);
        demo.testLiskov(new ChildGood(), -1);
        demo.testLiskov(new ChildBad(), -1);
    }
}
