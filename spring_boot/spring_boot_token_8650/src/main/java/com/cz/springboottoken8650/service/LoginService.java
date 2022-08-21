package com.cz.springboottoken8650.service;

import com.cz.springboottoken8650.data.MemberHolder;
import com.cz.springboottoken8650.entity.MemberUser;
import com.cz.springboottoken8650.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc: 登录、登出Service
 * @author: cao_wencao
 * @date: 2019-12-13 13:40
 */
@Service
public class LoginService {

    @Autowired
    private MemberHolder memberHolder;

    //使用RedisTemplate操作Redis
    @Autowired
    private RedisUtil redisUtil;


    /**
     * @desc: 登录接口，将token缓存拿到redis
     * @auth: cao_wencao
     * @date: 2019/12/13 13:52
     */
    public MemberUser login(String username, String password) {
        MemberUser memberUser = memberHolder.getMember(username);
        return memberUser;
    }

    /**
     * @desc: 登出接口、删除redis中token的缓存
     * @auth: cao_wencao
     * @date: 2019/12/13 13:53
     */
    public boolean logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        Boolean delete = redisUtil.del(token);
        if (!delete) {
            return false;
        }
        return true;
    }

//    /**
//     * @desc: 用户注册接口
//     * @auth: cao_wencao
//     * @date: 2019/12/16 16:07
//     */
//    public Integer insertUser(MemberUser memberUser){
//       return memberUserDao.insertUser(memberUser);
//    }
}
