package com.cz.encrypt.demo.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author jiaozi<liaomin @ gvt861.com>
 * @since JDK8
 * Creation time：2019/8/13 18:08
 */
public interface SecurityService {
    /**
     * 检查appid是否有效
     *
     * @return
     */
    public boolean checkAppId(String appid);

    /**
     * 检查版本号
     *
     * @param version
     * @return
     */
    public boolean checkVersion(String version);

    /**
     * 通过appid获取公钥
     *
     * @param appid
     * @return
     */
    public String getPublicKey(String appid);


    /**
     * 通过appid获取公钥
     *
     * @param appid
     * @return
     */
    public String getprivateKey(String appid);

    /**
     * 检测签名是否有效
     *
     * @param publicKey 公钥base64
     * @param data      签名字符串
     * @return
     */
    public boolean checkSign(String publicKey, String data) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException;

}
