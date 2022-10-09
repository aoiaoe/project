package com.cz.spring_cloud_alibaba.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * 非对称加密算法 生成公私钥
 * @author jzm
 * @date 2022/10/9 : 15:01
 */
@Slf4j
public class RsaTest {

    @Test
    public void generateKeyBytes() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();
        RSAPrivateKey priKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();

        System.out.println("私钥" + Base64.getEncoder().encodeToString(priKey.getEncoded()));
        System.out.println("公钥" + Base64.getEncoder().encodeToString(pubKey.getEncoded()));

    }
}
