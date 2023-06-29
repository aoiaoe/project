package com.cz.netty_rpc_server.serivce;

import com.cz.netty_rpc.api.IUserService;
import com.cz.netty_rpc.pojo.User;
import com.cz.netty_rpc_server.anno.RpcService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RpcService
@Service
public class UserServiceImpl implements IUserService {

    Map<Integer, User> map = new HashMap<>();

    @Override
    public User getUserById(int id) {
        if(map.size() == 0){
            User user1 = new User();
            user1.setId(1);
            user1.setName("张三");

            User user2 = new User();
            user2.setId(2);
            user2.setName("李四");
            map.put(1, user1);
            map.put(2, user2);
        }
        return map.get(id);
    }
}
