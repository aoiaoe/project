package com.geekbang.designpattern._03_proxy.static_proxy;

import com.geekbang.designpattern._03_proxy.ITargetService;

/**
 * @author jzm
 * @date 2023/4/21 : 11:02
 */
public class TargetProxy implements ITargetService {

    private ITargetService targetService;
    public TargetProxy(ITargetService service){
        this.targetService = service;
    }
    @Override
    public void doSth() {
        System.out.println("业务逻辑之前的预处理");
        targetService.doSth();
        System.out.println("业务逻辑之后的收尾处理");
    }
}
