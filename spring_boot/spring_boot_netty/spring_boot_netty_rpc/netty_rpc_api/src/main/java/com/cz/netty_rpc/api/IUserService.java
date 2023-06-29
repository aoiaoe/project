package com.cz.netty_rpc.api;

import com.cz.netty_rpc.pojo.User;

public interface IUserService {

    User getUserById(int id);
}
