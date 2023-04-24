package com.geekbang.designpattern._06_reponsibility_chain;

/**
 * @author jzm
 * @date 2023/4/24 : 10:26
 */
public class ResponsibilityChainDemo {

    public static void main(String[] args) {

        HandlerChain chain = new HandlerChain();
        chain.addHandler(new AHandler());
        chain.addHandler(new BHandler());

        chain.handle(new Object());
    }
}
