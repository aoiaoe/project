package com.cz.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 安全帮助类
 *
 * @author jiaozi《liaomin艾特gvt861.com》
 * @since JDK8
 * Creation time：2019/8/7 16:03
 */
public class SecurityUtils {
    /**
     * 非对称加密算法
     */
    public static final String ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHM = "SHA1WithRSA";

    /**
     * 读取公钥字节数组转换为对象
     *
     * @param bt 公钥字节数组
     * @return 公钥
     * @throws NoSuchAlgorithmException 算法错误
     * @throws InvalidKeySpecException  不合法key
     */
    public static PublicKey getPubKey(byte[] bt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec x = new X509EncodedKeySpec(bt);
        KeyFactory fac = KeyFactory.getInstance(ALGORITHM);
        return fac.generatePublic(x);
    }

    /**
     * 读取公钥base64串转换为对象
     *
     * @param base64Str base64Str
     * @return 公钥
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    public static PublicKey getPubKey(String base64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decode = Base64.getDecoder().decode(base64Str);
        return getPubKey(decode);
    }

    /**
     * 读取私钥字节数组转换为对象
     *
     * @param bt 私钥字节数组
     * @return 私钥
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    public static PrivateKey getPriKey(byte[] bt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec x = new PKCS8EncodedKeySpec(bt);
        KeyFactory fac = KeyFactory.getInstance(ALGORITHM);
        return fac.generatePrivate(x);
    }

    /**
     * 读取公钥base64串转换为对象
     *
     * @param base64Str base64串
     * @return 私钥
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    public static PrivateKey getPriKey(String base64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decode = Base64.getDecoder().decode(base64Str);
        return getPriKey(decode);
    }

    /**
     * 生成公私密钥对
     *
     * @return 生成公司密钥对
     * @throws NoSuchAlgorithmException 算法错误
     */
    public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
        // 生成DH密钥对
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }

    /**
     * key转换成base64
     *
     * @param key 密钥
     * @return base64字符串
     */
    public static String base64Key(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 使用平台公钥（Base64字符串）加密
     *
     * @param platformPubKey 平台公钥
     * @param data           原始数据
     * @return 加密数据
     * @throws InvalidKeySpecException   InvalidKeySpecException
     * @throws NoSuchAlgorithmException  NoSuchAlgorithmException
     * @throws NoSuchPaddingException    NoSuchPaddingException
     * @throws BadPaddingException       BadPaddingException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws InvalidKeyException       InvalidKeyException
     */
    public static byte[] encrypt(PublicKey platformPubKey, byte[] data) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cip = Cipher.getInstance(SecurityUtils.ALGORITHM);
        cip.init(Cipher.ENCRYPT_MODE, platformPubKey);
        byte[] mw = cip.doFinal(data);
        return mw;
    }

    /**
     * 使用个人私钥(Base64字符串)解密
     *
     * @param personPriKey 个人私钥
     * @param data         数据
     * @return 签名数据
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     * @throws InvalidKeyException      InvalidKeyException
     * @throws SignatureException       SignatureException
     */
    public static byte[] sign(PrivateKey personPriKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Signature si = Signature.getInstance(SecurityUtils.SIGN_ALGORITHM);
        si.initSign(personPriKey);
        si.update(data);
        return si.sign();
    }

    /**
     * 使用平台私钥解密用户数据
     *
     * @param platformPriKey 平台私钥
     * @param data           加密数据
     * @return 解密数据
     * @throws InvalidKeySpecException   InvalidKeySpecException
     * @throws NoSuchAlgorithmException  NoSuchAlgorithmException
     * @throws NoSuchPaddingException    NoSuchPaddingException
     * @throws InvalidKeyException       InvalidKeyException
     * @throws BadPaddingException       BadPaddingException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     */
    public static byte[] decrypt(PrivateKey platformPriKey, byte[] data) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cip = Cipher.getInstance(SecurityUtils.ALGORITHM);
        cip.init(Cipher.DECRYPT_MODE, platformPriKey);
        return (cip.doFinal(data));
    }

    /**
     * 验证签名是否有效
     *
     * @param personPubKey 个人公钥
     * @param data         数据
     * @param hash         hash值
     * @return 是否验证通过
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     * @throws InvalidKeyException      InvalidKeyException
     * @throws SignatureException       SignatureException
     */
    public static boolean validateSign(PublicKey personPubKey, byte[] data, byte[] hash) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature si = Signature.getInstance(SecurityUtils.SIGN_ALGORITHM);
        si.initVerify(personPubKey);
        si.update(data);
        return si.verify(hash);
    }
}
