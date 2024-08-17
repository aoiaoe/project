package com.cz.spring_cloud_alibaba.utils;

import cn.hutool.core.codec.Base64Decoder;
import com.alibaba.fastjson.JSON;
import com.cz.spring_cloud_alibaba.constants.CommonConstants;
import com.cz.spring_cloud_alibaba.constants.JwtConstants;
import com.cz.spring_cloud_alibaba.domain.auth.JwtUserInfo;
import com.cz.spring_cloud_alibaba.enums.ErrorEnums;
import com.cz.spring_cloud_alibaba.exception.BizException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(Base64Decoder.decode(CommonConstants.PUB_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(pubSpec);
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJqd3RfdXNlciI6IntcImlkXCI6MSxcIm5hbWVcIjpcImp6bVwifSIsImV4cCI6MTY2NTMxNzkzMX0.HYVQDOD1TbXtcOdFXnbOScS8JUOVW-y4kQ18qqmzBlaG7mYt5p8ds0pvrfSM66iFegaj9ZsTXnIoaVOOKU9_rjHBcJqOIecCGMzRIoJS7Bqp_yWUCCXANrsF9_nlTeNaPYREMH0fXG3NoT-N1q-6wzjaFtgdpuxW563adGxKEwXTrt_21D4eH7UzLkKZBvBJiOoMz2hbgr9ZUqhaln7a7wVngP7YCIWyl2Fb0rD7wJ3ifQprJ-NcKNRdLTjQCaWb2fFngq5yRHYoSvbqZmvaTwFrUOrwadQhP1QhP5d3BqHtTGA92HQ4qOo0wwC3MfpmpW7h_an2VbW-KBpzAL4QSA";
        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(Base64Decoder.decode(CommonConstants.PUB_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubSpec);
        System.out.println(verify(token, publicKey));
    }
}
