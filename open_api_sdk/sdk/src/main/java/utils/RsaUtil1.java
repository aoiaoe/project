package utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

@Slf4j
public class RsaUtil1 {


    /**
     * 算法加解密算法
     */
    private static final String ALGORITHM = "RSA";

    /**
     * 最大加密字节数，超出最大字节数需要分组加密
     */
    private static final Integer MAX_ENCRYPT_BLOCK = 117;

    private static final Integer MAX_DECRYPT_BLOCK = 128;

    /**
     * 请求报文公钥解密
     *
     * @param publicKeyString 公钥
     * @param text            报文
     * @return 加密报文
     */
    public static String encrypt(String publicKeyString, String text) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyString);
            return encryptRSA(publicKey, text);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RsaUtil encrypt exception! publicKeyString={} text={}", publicKeyString, text);
            return null;
        }
    }

    /**
     * 应答报文公钥解密
     *
     * @param publicKeyString 公钥
     * @param text            应答密文
     * @return 解密报文
     */
    public static String decrypt(String publicKeyString, String text) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyString);
            return decryptRSA(publicKey, text);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RsaUtil decrypt exception! publicKeyString={} text={}", publicKeyString, text);
            return null;
        }
    }

    /**
     * RSA 加密
     *
     * @param key  密钥
     * @param text 原文
     * @return 密文
     * @throws Exception 异常
     */
    private static String encryptRSA(Key key, String text) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 对加密进行初始化 第一个参数是加密模式，第二个参数是你想用的公钥加密还是私钥加密
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 分段加密
        byte[] make = doCrypt(text.getBytes(), cipher, MAX_ENCRYPT_BLOCK);
        return Base64.encode(make);
    }

    /**
     * RSA 解密
     *
     * @param key  密钥
     * @param text 密文
     * @return 明文
     * @throws Exception 异常
     */
    private static String decryptRSA(Key key, String text) throws Exception {
        // 创建加解密对象
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 对解密进行初始化 第一个参数是加密模式，第二个参数是你想用的公钥解密还是私钥解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        //分段解密
        byte[] make = doCrypt(Base64.decode(text), cipher, MAX_DECRYPT_BLOCK);
        return new String(make);
    }

    /**
     * 分段加解密
     *
     * @param data     要加解密的内容数组
     * @param cipher   加解密对象
     * @param maxBlock 分段大小
     * @return 结果
     * @throws IllegalBlockSizeException 异常
     * @throws BadPaddingException       异常
     */
    private static byte[] doCrypt(byte[] data, Cipher cipher, Integer maxBlock) throws IllegalBlockSizeException, BadPaddingException {
        int inputLength = data.length;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache;
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > maxBlock) {
                cache = cipher.doFinal(data, offSet, maxBlock);
                offSet += maxBlock;
            } else {
                cache = cipher.doFinal(data, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        return resultBytes;
    }

    /**
     * 获取私钥
     *
     * @param privateKeyString 私钥路径
     * @return 私钥
     */
    private static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        // 创建私钥key的规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        // 返回私钥对象
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKeyString 公钥
     * @return 公钥
     * @throws Exception 异常
     */
    private static PublicKey getPublicKey(String publicKeyString) throws Exception {
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        // 创建公钥key的规则
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        // 返回公钥对象
        return keyFactory.generatePublic(keySpec);
    }
}

