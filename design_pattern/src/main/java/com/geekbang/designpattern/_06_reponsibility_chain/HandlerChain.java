package com.geekbang.designpattern._06_reponsibility_chain;

/**
 * @author jzm
 * @date 2023/4/24 : 10:23
 */
public class HandlerChain {

    private Handler head;
    private Handler tail;

    public void addHandler(Handler handler){
        handler.setSuccessor(null);

        if(head == null){
            head = handler;
            tail = handler;
            return;
        }

        tail.setSuccessor(handler);
        tail = handler;
    }

    public void handle(Object param){
        if(head != null) {
            head.handle(param);
        }
    }

}
