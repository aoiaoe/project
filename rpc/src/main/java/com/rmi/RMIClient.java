package com.rmi;

import com.rmi.service.UserService;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        // 获取registry实例
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8899);
        // 通过registry查找远程对象
        UserService userService = (UserService) registry.lookup("usersService");
        System.out.println(userService.getUserById(1L));

    }
}
