package com.cz.spring_cloud_alibaba.utils;

import com.alibaba.fastjson.JSON;
import com.cz.spring_cloud_alibaba.constants.CommonConstants;
import com.cz.spring_cloud_alibaba.constants.JwtConstants;
import com.cz.spring_cloud_alibaba.domain.auth.JwtUserInfo;
import com.cz.spring_cloud_alibaba.enums.ErrorEnums;
import com.cz.spring_cloud_alibaba.exception.BizException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author jzm
 * @date 2022/10/9 : 16:51
 */
@Slf4j
public class JwtUtils {

    /**
     * 私钥生成token
     *
     * @param info
     * @param expiration
     * @param key
     * @return
     */
    public static String generateToken(JwtUserInfo info, Long expiration, Key key) {
        try {
            return Jwts.builder()
                    .claim(JwtConstants.JWT_USER, JSON.toJSONString(info))
                    .setExpiration(Date.from(LocalDateTime.now().plusSeconds(expiration).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key, SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            log.error("[生成jwt] 出错：", e);
            throw BizException.error(ErrorEnums.USER_LOG_ERROR);
        }
    }

//    /**
//     * 校验token
//     *
//     * @param token
//     * @param priKey
//     * @return
//     */
//    public static boolean verify(String token, PrivateKey priKey) {
//        if (StringUtils.isBlank(token)) {
//            return false;
//        }
//        try {
//            JwtUserInfo info = getClaims(token, priKey);
//            if (info != null) {
//                return true;
//            }
//        } catch (Exception e) {
//            log.error("[验证token] 出错： ", e);
//        }
//        return false;
//    }

    /**
     * 公钥校验token
     *
     * @param token
     * @param pubKey
     * @return
     */
    public static boolean verify(String token, PublicKey pubKey) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            JwtUserInfo info = getClaims(token, pubKey);
            if (info != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("[验证token] 出错： ", e);
        }
        return false;
    }

    /**
     * 公钥校验token
     *
     * @param token
     * @param pubKey
     * @return
     */
    public static boolean verify(String token, String pubKey) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            JwtUserInfo info = getClaims(token, publicKey(pubKey));
            if (info != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("[验证token] 出错： ", e);
        }
        return false;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param key
     * @return
     */
    public static JwtUserInfo getClaims(String token, Key key) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();
            Claims body = parser.parseClaimsJws(token).getBody();
            Object infoObj = body.get(JwtConstants.JWT_USER);
            if (infoObj != null) {
                return JSON.parseObject(infoObj.toString(), JwtUserInfo.class);
            }
            throw BizException.error(ErrorEnums.USER_UN_LOG_ERROR);
        } catch (Exception e) {
            log.error("[解析jwt] 出错：", e);
            throw BizException.error(ErrorEnums.USER_UN_LOG_ERROR);
        }
    }

    public static PublicKey publicKey(String key) throws Exception{
        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(CommonConstants.PUB_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(pubSpec);
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJqd3RfdXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcImp6bVwifSIsImV4cCI6MTY2NTMxNjEzOH0.Mpnr34DUCDvhL2TrYqGkx3ATyUcwoLfuM5Zt-eGy7K3-yRIRUsL0LD3-caTtwNmEzE1lsrCTfM_BVDds9aOglhkPva80HGTS1XRgkgH9IzRdpvaxA571SyKW0247XlJRlo13cFRR-MjlugsvYvFlPxYp7JZWbmqjAtsbevkd2NyA-ssrd5Cw2AAchz_racMa1gkDahuKP1Rh-VHpGV9vKvU0V8k6pyLo0LzxmBTOm4BW99EhvK0MxUTOsZ2UcawMZ6CW7kFCl114nKaqxpescoeSq8kKYMGGl9KZTwElqotiuAt97lHFWfCaFe7kZjlQWjgUBhsBqfkhd-ygEwlOHQ";
        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(CommonConstants.PUB_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubSpec);
        verify(token, publicKey);
    }
}
