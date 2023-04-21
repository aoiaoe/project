package com.geekbang.designpattern._03_proxy;

import com.geekbang.designpattern._03_proxy.ITargetService;

/**
 * @author jzm
 * @date 2023/4/21 : 11:01
 */
public class TargetServiceImpl implements ITargetService {

    @Override
    public void doSth() {
        System.out.println("业务逻辑");
    }
}
