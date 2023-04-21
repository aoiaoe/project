package com.geekbang.principle.composite_better_than_extend.good;

public class Ostrich implements Tweetable, EggLayable {// 鸵鸟
    private TweetAbility tweetAbility = new TweetAbility(); // 组合
    private EggLayAbility eggLayAbility = new EggLayAbility(); // 组合

    //... 省略其他属性和方法...
    @Override
    public void tweet() {
        tweetAbility.tweet(); // 委托
    }

    @Override
    public void layEgg() {
        eggLayAbility.layEgg(); // 委托
    }
}