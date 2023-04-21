package com.geekbang.designpattern._03_proxy.static_proxy;

import com.geekbang.designpattern._03_proxy.ITargetService;
import com.geekbang.designpattern._03_proxy.TargetServiceImpl;

/**
 * 静态代理
 * 硬编码
 * @author jzm
 * @date 2023/4/21 : 11:03
 */
public class Demo {

    public static void main(String[] args) {
        ITargetService targetService = new TargetServiceImpl();
        TargetProxy targetProxy = new TargetProxy(targetService);
        targetProxy.doSth();
    }
}
