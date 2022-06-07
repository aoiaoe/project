package com.cz.open_api_sdk_server.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cz.open_api_sdk_server.dto.HelloDto;
import com.cz.open_api_sdk_server.enums.ResponseCode;
import com.cz.open_api_sdk_server.exception.ApiException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
    public static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4rZf7iU1tJ6aNv78Cl0fdmXqxz0/DAA9XJsWwhl1OUfO5WTd6ghLL0JFBxztf8gh1fuwQw+vgLSuOzoomDP6qAehgo2FI3O9Iz60vujkwOKsb/rvJFSlA47INSHAk2dzE6Br6ZkdmjM8hmP3mvvS+5B1uEXyVSY02Jr+gosfO3JAxBL5EdC5mdQEIeZSChiwyh6TwIJNDm+/U8u0OAEAz3MAMWqvxAvXud6vCgV98NcP4mP/2/e0yWpYZZ2HC7t8xdKNvn6U6CApzBinBDFxS4hL/5DM7OVMTJrGr0P2qRd7X6KVbpYVJu2IV09x9mptSEkRku0g/wBHD98/jKvdbAgMBAAECggEAN3WiO56bCtmqcc0QbpXBtjGeIAGy4UuGT9TL71wabye7OoEpYPfo7x4eUROkqAaGNcq817gOgM7v0r4BzLWAUMur3kGSOJBop4P+bWPOQsXx1MFl4VgigGMgmE6VputqGhPd7i6C6LAGj/9i2EWmOCFHGUPVFvv0lTuDtpjLyjn9VGZHPePLGT4DRJ6dnMr/1Sw/kf5Z2ZFhVbqI68wTbY3Y06kkhXyEgNjbfOjJ5mT+g0S/y6pvXuICFhcQaYJ64PKnyAoTPfVlM4xGYrrsoJ8KL9Toq0Dl12uKAJdNfPGESnFm8fNOTvF2059FRkAqhsZPA55AUMnxAhmH3iOdqQKBgQDth4TVvQAKP4gwvyKpolt1bL4ihAZUyOvwRiwPMDrB7xEJ1oIrj1nzqIHYs60CMkAWJ3bFVA1Likw7hhElmN0+w1ARvgn/qEwg26H2A3VmKjKNJixEP+Dm2NctiLmZb/ynNXcz7JhHt6bMIP70nK0ZqFRAKLSCZWzWuLy6FJDG9wKBgQDHCffXg15ukTvQeS64rKzicw7/qwVKgyBYVRB7AVV7pWK5OLHamyprml/AvxcVCwSWNHflOVr3kREjR42qk8gudAc9pLWC6rxg74YZtmsl0v8ripEZhvJ7Y+33uEHOzsQl2pZdwwz6HFOmIvRzQoezLuo0pjP2MCUiguEm5zTFvQKBgAJtpkABJKT2LVBCQ/pz/Hdg2zjDHMV1IGRec0jcd11QMl5lYQNM/eBt0pYCkDw7g0g2sxIlX9Vr3oelQTufKnPfz0u/I58He2/Cjw9Szbpzp/ylbUJtXME/akO+6BbXfD/BXiybuJrTF9NyaujZSTO/8syNHftPSiOP0+4bE7fVAoGAWeyF/edLPbb+CCUH18Wj4jdLenUg0dZGQcJEzN4AafmBjd8wc2yREMZRJnrvCcHNk7Mmy/hKwfjXflHwjjVwRpSDRPVqUDiPXVg6BNnmtNMMF/blsVvHp7mxJK8phC7kbZnBP6FE9qbfn/rA6GYRewHiLGE+rqZ30IzC/lmM/R0CgYEArve+xfzm35TBpQKLMIEQ7lDBcGXc1ozSbxQPcGPFSlKN/F224iQOl1TdGXiX3+XYxYQvYHh2vfc/9PiXTU7Q2txOxbqxSIHPGb2Y1XrKOv4eKhLNyQd3l4y3fp3ofjvte+Eh9/fmNd4MhEQJByTaAHhWx0XeZ0ymlSW1aWEUfwM=";
    public static String PUBLIC_KEY_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuK2X+4lNbSemjb+/ApdH3Zl6sc9PwwAPVybFsIZdTlHzuVk3eoISy9CRQcc7X/IIdX7sEMPr4C0rjs6KJgz+qgHoYKNhSNzvSM+tL7o5MDirG/67yRUpQOOyDUhwJNncxOga+mZHZozPIZj95r70vuQdbhF8lUmNNia/oKLHztyQMQS+RHQuZnUBCHmUgoYsMoek8CCTQ5vv1PLtDgBAM9zADFqr8QL17nerwoFffDXD+Jj/9v3tMlqWGWdhwu7fMXSjb5+lOggKcwYpwQxcUuIS/+QzOzlTEyaxq9D9qkXe1+ilW6WFSbtiFdPcfZqbUhJEZLtIP8ARw/fP4yr3WwIDAQAB";

    private static String ALGORITHM = "RSA";
    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }
 
    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }
 
    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }
 
    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
 
    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, String publicKeyString) throws Exception{
        PublicKey publicKey = string2PublicKey(publicKeyString);
        return publicEncrypt(content, publicKey);
    }


    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }
 
    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(String content, String privateKeyString) throws Exception{
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyString);
        //加密后的内容Base64解码
        byte[] base642Byte = RSAUtil.base642Byte(content);
        return privateDecrypt(base642Byte, privateKey);
    }

    //公钥解密
    public static byte[] publicDecrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //公钥解密
    public static byte[] publicDecrypt(byte[] content, String publicKeyString) throws Exception{
        PublicKey publicKey = string2PublicKey(publicKeyString);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥加密
    public static byte[] privateEncrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥加密
    public static String privateEncrypt(String content, String privateKeyString) throws Exception{
        PrivateKey privateKey = string2PrivateKey(privateKeyString);
        return byte2Base64(privateEncrypt(content.getBytes(), privateKey));
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }
 
    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }

    public static String encryptByPubKey(String publicKey, String data){
        try {
            return byte2Base64(publicEncrypt(data.getBytes(), publicKey));
        }catch (Exception e){
            throw new ApiException(ResponseCode.API_CRYPT_ERROR);
        }
    }

    public static String decryptByPubKey(String publicKey, String data) {
        try {
            return new String(publicDecrypt(data.getBytes(), publicKey));
        }catch (Exception e){
            throw new ApiException(ResponseCode.API_CRYPT_ERROR);
        }
    }

    public static void main(String args[]) throws Exception {
        RSAUtil rsaUtil = new RSAUtil();
//        rsaUtil.test3();
        rsaUtil.test4();
    }


    public void printKeys() throws Exception {
        KeyPair keyPair = RSAUtil.getKeyPair();
        String publicKeyStr = RSAUtil.getPublicKey(keyPair);
        String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
        System.out.println("RSA公钥Base64编码:" + publicKeyStr);
        System.out.println("RSA私钥Base64编码:" + privateKeyStr);
    }

    public void decodeByPrivateKey() throws Exception {
        String data = "NE9Lo08UzImy6IvuEToVjwAmXhcSAqDqcR9fH9FhGFkNYZosMW46GAgO0tL13572AB7as6/8ObWePo/Z3QXAI8Ouzu6T1oxgB7OoXmvPvWFxdpANDgNU/Y5yXIf3x8o1X4D+kGVVQ49HWs6TUOdOewNlToUny2RBEk/DRU7/EWuRhT/K9MAycDeQGBsnjsKe6iH+0Fz8Q/355ceUHGOFP8pP4tL0iemMb04I50X85bqKxVKY2bwkh/nMR3FBgqLkQWp+NQbjIqvpXCCR6igs6kNi1Al3UF4YLEqThaCmj2aJfacg6vFAMNjh/PjYCvfm3CMEcqTd+/qvXi54niKoSw==";
        PrivateKey privateKey = RSAUtil.string2PrivateKey(PRIVATE_KEY);
        //加密后的内容Base64解码
        byte[] base642Byte = RSAUtil.base642Byte(data);
        //用私钥解密
        byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
        //解密后的明文
        System.out.println("解密后的明文: " + new String(privateDecrypt));
    }


    // 公钥加密 私钥解密
    public void test1(){
        try {
            //===============生成公钥和私钥，公钥传给客户端，私钥服务端留==================
            //生成RSA公钥和私钥，并Base64编码
            KeyPair keyPair = RSAUtil.getKeyPair();
            String publicKeyStr = RSAUtil.getPublicKey(keyPair);
            String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
            System.out.println("RSA公钥Base64编码:" + publicKeyStr);
            System.out.println("RSA私钥Base64编码:" + privateKeyStr);

            //=================客户端=================
            //hello, i am infi, good night!加密
            String message = "hello, i am infi, good night!";
            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            //用公钥加密
            byte[] publicEncrypt = RSAUtil.publicEncrypt(message.getBytes(), publicKey);
            //加密后的内容Base64编码
            String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
            System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);


            //##############	网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################



            //===================服务端================
            //将Base64编码后的私钥转换成PrivateKey对象
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            //加密后的内容Base64解码
            byte[] base642Byte = RSAUtil.base642Byte(byte2Base64);
            //用私钥解密
            byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
            //解密后的明文
            System.out.println("解密后的明文: " + new String(privateDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 私钥加密 公钥解密
    public void test2(){
        try {
            //===============生成公钥和私钥，公钥传给客户端，私钥服务端留==================
            //生成RSA公钥和私钥，并Base64编码
            KeyPair keyPair = RSAUtil.getKeyPair();
            String publicKeyStr = RSAUtil.getPublicKey(keyPair);
            String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
//            System.out.println("RSA公钥Base64编码:" + publicKeyStr);
//            System.out.println("RSA私钥Base64编码:" + privateKeyStr);


            String message = "hello, i am infi, good night!";
            //===================服务端================
            //将Base64编码后的私钥转换成PrivateKey对象
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            //用私钥加密
            byte[] privateEncrypt = RSAUtil.privateEncrypt(message.getBytes(), privateKey);

            String encryptString = byte2Base64(privateEncrypt);
            //解密后的明文
            System.out.println("加密后的密文: " + encryptString);


            //##############	网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################

            //=================客户端=================
            //hello, i am infi, good night!加密

            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            //用公钥加密
            byte[] publicDecrypt = RSAUtil.publicDecrypt(base642Byte(encryptString), publicKey);

            System.out.println("公钥解密：" + new String(publicDecrypt));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 公钥加密 私钥解密
    public void test3(){
        try {

            //=================客户端=================
            //hello, i am infi, good night!加密
            HelloDto dto = new HelloDto();
            dto.setName("sep");
            dto.setChaos(123123);

            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(PUBLIC_KEY_STRING);
            //用公钥加密
            byte[] publicEncrypt = RSAUtil.publicEncrypt(JSONObject.toJSONString(dto).getBytes(), publicKey);
            //加密后的内容Base64编码
            String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
            System.out.println(byte2Base64);

            //##############	网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################



            //===================服务端================
            //将Base64编码后的私钥转换成PrivateKey对象
            PrivateKey privateKey = RSAUtil.string2PrivateKey(PRIVATE_KEY);
            //加密后的内容Base64解码
            byte[] base642Byte = RSAUtil.base642Byte(byte2Base64);
            //用私钥解密
            byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
            //解密后的明文
            System.out.println("解密后的明文: " + new String(privateDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 公钥加密 私钥解密
    public void test4(){
        try {

            //=================客户端=================
            //hello, i am infi, good night!加密
            HelloDto dto = new HelloDto();
            dto.setName("sep");
            dto.setChaos(123123);

            String encrypted = encryptByPubKey(PUBLIC_KEY_STRING, JSON.toJSONString(dto));

            System.out.println(encrypted);
            //将Base64编码后的私钥转换成PrivateKey对象
            byte[] bytes = privateDecrypt(encrypted, PRIVATE_KEY);
            System.out.println(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}