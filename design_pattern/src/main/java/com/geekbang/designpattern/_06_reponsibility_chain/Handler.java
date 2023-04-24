package com.geekbang.designpattern._06_reponsibility_chain;

/**
 * @author jzm
 * @date 2023/4/24 : 10:15
 */
public abstract class Handler {

    private Handler successor;

    public void handle(Object param){
        if(!doHandle(param) && successor != null){
            successor.handle(param);
        }
    }

    public void setSuccessor(Handler successor){
        this.successor = successor;
    }

    protected abstract boolean doHandle(Object param);
}
