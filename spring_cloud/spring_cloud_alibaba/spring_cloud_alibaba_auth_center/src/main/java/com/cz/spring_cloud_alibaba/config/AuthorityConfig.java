package com.cz.spring_cloud_alibaba.config;

import cn.hutool.core.codec.Base64Encoder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author jzm
 * @date 2022/10/9 : 15:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class AuthorityConfig {

    private final String PRI_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCFLVbx7by+3K5aTxK5VS7V8118SCJQGKqN++IaxM0J/oPLEm6uLmY1m9yM3EAPdXC9RCdUfDjtSyls2OGVUlTnOpEoYDl23dW8x8eVwawU6f17yTWFoLsl8ckIeT6uHtHmaAT4h6PkmxWvNQBTkh0rBI9XwdTLxLhOrMSkAVCN81GhHoUNOQptlR0QmZ+Zu1sBPPOM9ZHApfP9s95cQZeQT1wSkyKaLEkZlMMjLD8q5mQEbx4gHXALZtxYBJVLFqsEcTBERHEd/EiHI+UE+HHNcXw602GvuxoXvICrhb0Z665z/YiRqW8UFmjtjlqk19prybyJKyk4dBTaRIRTCXdPAgMBAAECggEAEyomI+R5T+ID+Rz97cuzVLiT0Gm0/l++U3ZW4FFHzFNi9dsuMESfEsHeKCsd0NuuoJ3pJwBFVOiHCpHfTJSvgSLtdqj6O//KXUNGlmdrcwsFHkYjPJqXs+sqadzAx1/KBuUbKgSi8ifbI/51kJErP2CFnDRZggVKHJBCL/O8euC+THLFzHClhH4zmEa48Tphj4hbuf0zRWgWtGsVLo9A/iYfY0RwEmqrzx3Ceoi0g5hVs9aSagXiiaEHOgYYhzkVpHouGn8QEjAEIrPrSyyuIVFYn7+1VTFw78wwx1rgcDcQMuO80/X5pVv1SICGBCuu3MREvpoEnbS4qWJVYmVBgQKBgQDUDb+IBRQ/p80pMxquVjIDbjUmHiiPJbBdEDly+g+5Hfl1FsRmRhQhuAwacY526kSYb827FLntVkUHjp1xy93sSQS0RyjHx4z9QQ1MLcIeL4n+qrfes11co4o6fccG0uFGbEIpBCPkn33sEQfyJxYIqh2kGIZwCaOljahf0i0kDwKBgQCgxuOw95LPWzEwhS+luNG3EjRryNdYXZNnoMz6EJ0aGW8h0oaFot/ucAN4xpgUoQa81h9pfR274xmAcoEnv/++xWryzx7nRk77uIcxTxVIka0hnzDYVLNZsPgyRFasNFMPnyxGZeVfJp7EkJR0eGQBykYEp7nj44GR59l+2QI4wQKBgQClRd3c3tCMWHWfwdPuwwGSCOhlwCWuhBp0Pjv4cAunJUSTTN5gNAhmy3KrfYwjBK3X/XPkdt/+qjl9DVuLIPDjw3lmJPwU0WQC0xvRHuzn5/duxJIwga2nkVMgCX4AxIWOBLyACVkMTH6xagi+ZFZZBHW7ut21Ht4sHz93B3HlYwKBgQCgY6lTqrdPmraoo8VTutujmUqoWBTUN43MrHKv6JEBL2xZPm8qs8BXWnFsNBoVQkq5aZauor1d6YFQTWqhH/3e74zwq0U2DULQBVcBDta5rXIc/3U36s9843nGk7wvUIeoJu+BTzeSQ7HRgDZJNqz1yDcvf6+HMzfYOYYU8CIyQQKBgQCedPOpENyfTmSRuoUNedUAQFT3nQKVuYBE5sBAE0uorjomBZxT3ZricNbRajc0NC5Z+40z3DmfPyu84o5uvT1dY6+v1SYkOX/LZ4eZ7RQWlCsECigjB5i+TB2jg/rp46K8ZtSGHSo1rxIvRsUc92QiH7FNh5MtPuqWEe16PB8pvQ==";

    /** jwt token 过期时间*/
    private Long expiration = 1800L;

    @Bean
    public Key privateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(PRI_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }

}
