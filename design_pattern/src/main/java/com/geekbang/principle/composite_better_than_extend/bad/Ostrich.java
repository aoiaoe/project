package com.geekbang.principle.composite_better_than_extend.bad;

/**
 * 鸵鸟不会飞，不应该使用继承让其具备飞行能力
 */
public class Ostrich extends AbstractBird { // 鸵鸟
    //... 省略其他属性和方法...
    public void fly() {
        throw new UnsupportedOperationException("I can't fly.'");
    }
}