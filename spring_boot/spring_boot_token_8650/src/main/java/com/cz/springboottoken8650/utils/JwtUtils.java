package com.cz.springboottoken8650.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cz.springboottoken8650.entity.MemberUser;
import com.cz.springcloudsdk.error.ServiceException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author: hs
 * @Date: 2019/6/11 20:58
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.jwt")
public class JwtUtils {

    private static final long EXPIRE_TIME = 30 * 60;

    private static final String SECRET = "HYBI%&J)(UJJ&TG&";

    private Long expire;

    private String secret;


    /**
     * @return 加密的token
     */
    public String sign(MemberUser memberUser) {
        Date date = new Date(System.currentTimeMillis() + expire * 1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withSubject(memberUser.getId().toString())
                .withIssuer(memberUser.getUsername())
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public Map<String, Claim> verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(username).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            throw new ServiceException("鉴权失败,无效的用户", e);
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuer();
        } catch (JWTDecodeException e) {
            throw new ServiceException("无效的token，请重新登录", e);
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的id
     */
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            throw new ServiceException("无效的token，请重新登录", e);
        }
    }
}
