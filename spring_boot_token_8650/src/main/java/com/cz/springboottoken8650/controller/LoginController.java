package com.cz.springboottoken8650.controller;

import com.alibaba.fastjson.JSONObject;
import com.cz.springboottoken8650.entity.MemberUser;
import com.cz.springboottoken8650.result.ResponseCode;
import com.cz.springboottoken8650.service.LoginService;
import com.cz.springboottoken8650.utils.JwtUtils;
import com.cz.springboottoken8650.utils.MD5Util;
import com.cz.springboottoken8650.utils.RedisUtil;
import com.cz.springboottoken8650.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @desc: 登录接口控制器
 * @author: cao_wencao
 * @date: 2019-12-13 13:40
 */
@RestController
@RequestMapping(value = "api/member")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * @desc: 用户登录
     * @auth: cao_wencao
     * @date: 2019/12/16 11:11
     */
    @PostMapping(value = "/login")
    public ResponseCode login(@RequestBody MemberUser memberUser) {
        // 1.验证参数
        String userName = memberUser.getUsername();
        if (StringUtils.isEmpty(userName)) {
            return ResponseCode.error("用户名称不能为空!");
        }
        String password = memberUser.getPassword();
        if (StringUtils.isEmpty(password)) {
            return ResponseCode.error("密码不能为空!");
        }
        String newPassWord = MD5Util.encode(password);
        MemberUser user = loginService.login(userName, newPassWord);
        if (user == null) {
            return ResponseCode.error("账号或密码错误!");
        }
        //生成token
        String token = jwtUtils.sign(memberUser);//TokenUtils.getToken();
        //userId
        Integer userId = user.getId();
        //存放Redis
        redisUtil.set(token, String.valueOf(userId));
        log.info("【用户信息token存放在Redis中.....key为】：{},value为：{}", token, userId);
        //直接返回Token到客户端
        JSONObject JSONObject = new JSONObject();
        JSONObject.put("token", token);
        return ResponseCode.success("登录成功", JSONObject);
    }


    /**
     * @desc: 用户登出
     * @auth: cao_wencao
     * @date: 2019/12/16 11:11
     */
    @PostMapping(value = "/logout")
    public ResponseCode logout(HttpServletRequest request) {
        boolean flag = loginService.logout(request);
        if (flag) {
            return ResponseCode.success("注销成功");
        }
        return ResponseCode.success("注销失败");
    }


//    /**
//     * @desc: 用户注册
//     * @auth: cao_wencao
//     * @date: 2019/12/16 16:04
//     */
//    @RequestMapping(value = "/register")
//    public ResponseCode register(@RequestBody MemberUser memberUser){
//        // 参数验证
//        String passWord = memberUser.getPassword();
//        if (StringUtils.isEmpty(passWord)) {
//            return ResponseCode.error("密码不能为空!");
//        }
//        // MD5加密
//        String encodePassword = MD5Util.encode(passWord);
//        memberUser.setPassword(encodePassword);
//        memberUser.setCreated(new Date());
//        memberUser.setUpdated(new Date());
//        Integer result = loginService.insertUser(memberUser);
//        if (result <= 0) {
//            return ResponseCode.error("注册用户信息失败!");
//        }
//        return ResponseCode.success("用户注册成功");
//    }

}
