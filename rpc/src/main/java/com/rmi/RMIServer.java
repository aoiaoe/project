package com.rmi;

import com.rmi.service.UserService;
import com.rmi.service.UserServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * // 1、服务端提供根据ID查询用户的方法
 * // 2、客户端调用服务端方法、并返回对象
 * // 3、要求使用RMI进行远程通信
 */
public class RMIServer {

    public static void main(String[] args) {
        try {
            // 注册Registry实例,绑定端口
            Registry registry = LocateRegistry.createRegistry(8899);
            // 创建远程对象
            UserService service = new UserServiceImpl();
            // 将远程对象注册到RMO服务器上，即服务器注册表上
            registry.rebind("usersService", service);
            System.out.println("RMI服务器端启动");
        } catch (Exception e) {

        }
    }
}
