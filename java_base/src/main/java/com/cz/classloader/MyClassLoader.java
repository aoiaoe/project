package com.cz.classloader;

import java.io.*;
import java.lang.reflect.Method;

/**
 * 自定义类装载器,实现类覆盖
 * @author alian
 * @date 2020/7/3 下午 5:27
 * @since JDK8
 */
public class MyClassLoader extends ClassLoader {

    public MyClassLoader(ClassLoader parent){
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        ClassLoader system = getSystemClassLoader();
        try {
            clazz = system.loadClass(name);
        } catch (Exception e) {
            // ignore
        }
        if (clazz != null)
            return clazz;
        clazz = findClass(name);
        return clazz;
    }

    @Override
    public Class<?> findClass(String name) {

        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(new File("java_base/target/classes/com/cz/classloader/Test.class"));
            int c = 0;
            while (-1 != (c = is.read())) {
                baos.write(c);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) {
        MyClassLoader loader = new MyClassLoader(
                MyClassLoader.class.getClassLoader());
        Class clazz;
        try {
            clazz = loader.loadClass("com.cz.classloader.Test");
            Object object = clazz.newInstance();
            Method sayHi = clazz.getMethod("sayHi");
            sayHi.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
