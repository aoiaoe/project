package com.cz.encrypt;

import com.alibaba.fastjson.JSONObject;
import com.cz.encrypt.bean.BizBean;
import com.cz.encrypt.sign.BeanToUrlSign;
import com.cz.encrypt.sign.JsonToUrlSign;
import com.cz.encrypt.sign.MapToUrlSign;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author jiaozi《liaomin艾特gvt861.com》
 * @since JDK8
 * Creation time：2019/8/8 12:13
 */
public class ApolloSdk {
    /**
     * json数据中包装签名字符串放篡改
     * @param personPriKey 个人私钥
     * @param jsonData 业务json数据
     * @return 签名后base64字符串
     * @throws NoSuchAlgorithmException 算法错误
     * @throws InvalidKeySpecException 不合法key
     * @throws InvalidKeyException 不合法key描述
     * @throws SignatureException 签名异常
     * @throws NoSuchPaddingException  NoSuchPaddingException
     * @throws BadPaddingException BadPaddingException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public String wrapSign(PrivateKey personPriKey, /**PublicKey platformPubKey,**/String jsonData) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        JsonToUrlSign jsonTranslateUrlSign=new JsonToUrlSign(personPriKey,jsonData);
        byte[] sign = jsonTranslateUrlSign.sign();
        JSONObject jsonObject=JSONObject.parseObject(jsonData);
//        jsonObject.put(MapTranslateUrlSign.SIGN_KEY,new String(SecurityUtils.encrypt(platformPubKey,sign),charset));
        jsonObject.put(MapToUrlSign.SIGN_KEY,Base64.getEncoder().encodeToString(sign));
        return jsonObject.toJSONString();
    }

    /**
     * 对象包装签名
     * @param personPriKey 个人私钥
     * @param object 继承自BizBean的实体类
     * @param <T> 继承自BizBean实现类
     * @return 原始bean带上签名字符串
     * @throws NoSuchAlgorithmException 算法错误
     * @throws InvalidKeySpecException 不合法key
     * @throws InvalidKeyException 不合法key描述
     * @throws SignatureException 签名异常
     * @throws NoSuchPaddingException  NoSuchPaddingException
     * @throws BadPaddingException BadPaddingException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public <T extends BizBean> T wrapSign(PrivateKey personPriKey/**, PublicKey platformPubKey**/, T object) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        BeanToUrlSign<T> jsonTranslateUrlSign=new BeanToUrlSign<T>(personPriKey,object);
        byte[] sign = jsonTranslateUrlSign.sign();
        object.setSign(Base64.getEncoder().encodeToString(sign));
        return object;
    }

    /**
     * 验证json串是否合法
     * @param publicKey 公钥
     * @param json json字符串
     * @return 是否验证通过
     * @throws InvalidKeySpecException InvalidKeySpecException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeyException InvalidKeyException
     * @throws SignatureException SignatureException
     */
    public boolean validateSign(PublicKey publicKey,String json) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        JsonToUrlSign jsonTranslateUrlSign=new JsonToUrlSign(publicKey,json);
        return jsonTranslateUrlSign.validateSign();
    }
}
