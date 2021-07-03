package com.cz.encrypt.sign;


import com.cz.encrypt.bean.BizBean;
import com.cz.encrypt.url.BeanUrlParser;
import com.cz.encrypt.url.StringUrlParser;
import com.cz.encrypt.url.UrlParser;

import java.security.Key;
import java.security.PrivateKey;

/**
 * bean签名
 *
 * @author 《liaomin艾特gvt861.com》
 * @since JDK8
 * Creation time：2019/8/8 14:08
 */
public class BeanToUrlSign<T extends BizBean> extends AbstractUrlSign {
    private Key key;
    public static final String SIGN_KEY = "sign";
    public static final String APPID_KEY = "appid";
    T bean;
    private UrlParser urlParser;

    public BeanToUrlSign(PrivateKey privateKey, T bean) {
        this.key = privateKey;
        this.bean = bean;
        urlParser = new BeanUrlParser(bean);
    }


    @Override
    public String url() {
        String url = urlParser.parse();
        StringUrlParser stringUrlParser = new StringUrlParser(url);
        return stringUrlParser.parse();
    }

    @Override
    public Object data() {
        return this.bean;
    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public boolean allowdSign() {
        return true;
    }
}
