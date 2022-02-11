package com.cz.test.config;

import com.cz.securitysdk.constants.Constants;
import com.cz.securitysdk.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        // <1> 设置 LoginUser 的用户唯一标识。注意，这里虽然变量名叫 token ，其实不是身份认证的 Token
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        // <2> 设置用户终端相关的信息，包括 IP、城市、浏览器、操作系统
//        setUserAgent(loginUser);

        // <3> 记录缓存
        refreshToken(loginUser);

        // <4> 生成 JWT 的 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    private void setUserAgent(LoginUser loginUser) {
//        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
//        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
//        loginUser.setIpaddr(ip);
//        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
//        loginUser.setBrowser(userAgent.getBrowser().getName());
//        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 暂未引入过期机制
     * @param loginUser
     */
    private void refreshToken(LoginUser loginUser) {
//        loginUser.setLoginTime(System.currentTimeMillis());
//        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime);
        // 根据 uuid 将 loginUser 缓存
        String userKey = getTokenKey(loginUser.getToken());
//        stringObjectRedisTemplate.opsForValue().set(userKey, JSON.toJSONString(loginUser), tokenProperties.getExpireSeconds(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(userKey, loginUser, tokenProperties.getExpireSeconds(), TimeUnit.SECONDS);
    }

    private String getTokenKey(String token) {
        return Constants.LOGIN_TOKEN_KEY + token;
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecretKey()).compact();
    }

    public LoginUser getLoginUser(String jwtToken) {
        Claims claims = extractToken(jwtToken);
        if (claims == null){
            return null;
        }
        String redisToken = claims.get(Constants.LOGIN_USER_KEY, String.class);
        if(StringUtils.isEmpty(redisToken)){
            return null;
        }
        String userKey = getTokenKey(redisToken);
        Object object = redisTemplate.opsForValue().get(userKey);
        return object != null ? (LoginUser)object : null;
    }

    public Claims extractToken(String jwtToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e){
            log.error("解析jwt token出错:{}", e);
        }
        return null;
    }

    public boolean validToken(String jwtToken){
        return extractToken(jwtToken) != null;
    }

}