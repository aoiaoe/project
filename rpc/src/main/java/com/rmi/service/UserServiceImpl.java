package com.rmi.service;

import com.rmi.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    Map<Long, User> map = new HashMap<>();

    public UserServiceImpl() throws RemoteException {
        super();
        User user1 = new User();
        user1.setId(1L);
        user1.setName("张三");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("李四");
        map.put(1L, user1);
        map.put(2L, user2);
    }

    @Override
    public User getUserById(Long id) throws RemoteException {
        return map.get(id);
    }
}
