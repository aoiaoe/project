package com.cz.encrypt.sign;

import com.cz.encrypt.SecurityUtils;
import com.cz.encrypt.conf.CharsetGlobal;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;

/**
 * url签名 比如a=1&amp;b=2
 *
 * @author jiaozi《liaomin艾特gvt861.com》
 * @since JDK8
 * Creation time：2019/8/8 15:43
 */
public abstract class AbstractUrlSign implements RestSign {
    /**
     * 获取url
     *
     * @return 拼接url
     */
    public abstract String url();

    @Override
    public byte[] sign() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (!allowdSign()) {
            throw new SecurityException("数据中是否未包含appid字符串");
        }
        return SecurityUtils.sign((PrivateKey) getKey(), url().getBytes(CharsetGlobal.currentCharSet()));
    }

    @Override
    public boolean validateSign() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Map data = (Map) this.data();
        return SecurityUtils.validateSign((PublicKey) getKey(),
                url().getBytes(CharsetGlobal.currentCharSet()),
                Base64.getDecoder().decode(data.get(MapToUrlSign.SIGN_KEY).toString())
        );
    }
}
