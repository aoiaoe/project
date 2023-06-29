package com.rmi.service;

import com.rmi.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    User getUserById(Long id) throws RemoteException;
}
