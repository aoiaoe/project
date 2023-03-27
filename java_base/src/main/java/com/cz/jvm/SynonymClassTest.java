package com.cz.jvm;

/**
 * @author jzm
 * @date 2023/3/14 : 14:39
 */
public class SynonymClassTest {

    static javassist.ClassPool cp = javassist.ClassPool.getDefault();

    /**
     * -XX:MetaspaceSize=12m -XX:MaxMetaspaceSize=12m
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        for (int i = 0; ; i++) {
            Class c = cp.makeClass("eu.plumbr.demo.Generated" + i).toClass();
        }
    }
}
