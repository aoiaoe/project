package com.cz.script.js;

import cn.hutool.script.JavaScriptEngine;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.script.ScriptEngineManager;
import java.util.Set;
import java.util.stream.Collectors;

class KeywordCheckUtils7 {

    private static final Set<String> blacklist = Sets.newHashSet(
            // Java 全限定类名
            "java.io.File", "java.io.RandomAccessFile", "java.io.FileInputStream", "java.io.FileOutputStream",
            "java.lang.Class", "java.lang.ClassLoader", "java.lang.Runtime", "java.lang.System", "System.getProperty",
            "java.lang.Thread", "java.lang.ThreadGroup", "java.lang.reflect.AccessibleObject", "java.net.InetAddress",
            "java.net.DatagramSocket", "java.net.DatagramSocket", "java.net.Socket", "java.net.ServerSocket",
            "java.net.MulticastSocket", "java.net.MulticastSocket", "java.net.URL", "java.net.HttpURLConnection",
            "java.security.AccessControlContext", "java.lang.ProcessBuilder",
            //反射关键字
            "invoke", "newinstance",
            // JavaScript 方法
            "eval", "new function",
            //引擎特性
            "Java.type", "importPackage", "importClass", "JavaImporter",
            "for(", "while"
    );

    public KeywordCheckUtils7() {
        // 空构造方法
    }

    public static void checkInsecureKeyword(String code) throws Exception {
        // 去除注释
        String removeComment = StringUtils.replacePattern(code, "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*[\n\r\u2029\u2028])", " ");
        //去除特殊字符
        removeComment = StringUtils.replacePattern(removeComment, "[\u2028\u2029\u00a0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\ufeff]", "");
        // 去除空格
        String removeWhitespace = StringUtils.replacePattern(removeComment, "\\s+", "");
        // 多个空格替换为一个
        String oneWhiteSpace = StringUtils.replacePattern(removeComment, "\\s+", " ");
        System.out.println(removeWhitespace);
        System.out.println(oneWhiteSpace);
        Set<String> insecure = blacklist.stream().filter(s -> StringUtils.containsIgnoreCase(removeWhitespace, s) ||
                StringUtils.containsIgnoreCase(oneWhiteSpace, s)).collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(insecure)) {
            System.out.println("存在不安全的关键字:" + insecure);
            throw new Exception("存在安全问题");
        } else {
            JavaScriptEngine engine = new JavaScriptEngine();
            engine.eval(code);
        }
    }
}
