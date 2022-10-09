package com.cz.spring_cloud_alibaba_gateway.config;

import com.cz.spring_cloud_alibaba.constants.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author jzm
 * @date 2022/10/9 : 17:33
 */
@Configuration
public class JwtPubKeyConfig {

    @Bean
    public PublicKey publicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec priPKCS8 = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(CommonConstants.PUB_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(priPKCS8);
    }
}
