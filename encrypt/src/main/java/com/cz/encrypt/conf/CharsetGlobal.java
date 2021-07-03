package com.cz.encrypt.conf;

import java.nio.charset.Charset;

/**
 * 设置公共的字符集
 */
public class CharsetGlobal {
    private static ThreadLocal<Charset> charset = new ThreadLocal<>();

    public CharsetGlobal() {

    }

    public static void setGlobalCharSet(String charSetString) {
        charset.set(Charset.forName(charSetString));
    }

    public static Charset currentCharSet() {
        Charset curCharset = charset.get();
        if (curCharset == null) {
            curCharset = Charset.defaultCharset();
            charset.set(curCharset);
        }
        return curCharset;
    }
}
