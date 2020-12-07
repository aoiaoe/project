package com.cz.encrypt;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGenerator {

    /**
     * 非对称加密算法
     */
    public static final String ALGORITHM="RSA";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPair keyPair = genKeyPair();
        System.out.println("pub key : " + base64Key(keyPair.getPublic()));
        System.out.println("pri key : " + base64Key(keyPair.getPrivate()));
    }
    /**
     * key转换成base64
     * @param key 密钥
     * @return base64字符串
     */
    public static String base64Key(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 生成公私密钥对
     * @return 生成公司密钥对
     * @throws NoSuchAlgorithmException 算法错误
     */
    public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
        // 生成DH密钥对
        KeyPairGenerator kpg=KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(2048);
        KeyPair kp=kpg.generateKeyPair();
        return kp;
    }
}
