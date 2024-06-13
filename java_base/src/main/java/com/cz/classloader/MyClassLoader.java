package com.cz.classloader;

import java.io.*;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 自定义类装载器,实现类覆盖
 *
 * @author alian
 * @date 2020/7/3 下午 5:27
 * @since JDK8
 */
public class MyClassLoader extends ClassLoader {

    public MyClassLoader(ClassLoader parent) {
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
        resolveClass(clazz);
        return clazz;
    }

    @Override
    public Class<?> findClass(String name) {

        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
//            is = new FileInputStream(new File("/Users/sephiroth/study/IdeaProjects/project/java_base/src/main/java/com/cz/classloader/Test.class"));
            is = new FileInputStream(new File("G:\\workspace\\project\\java_base\\src\\main\\java\\com\\cz\\classloader\\Test1.class"));
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
        new Thread(() -> {
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    Class.forName("com.cz.classloader.Test1", true, loader);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(1);
                    Class<?> aClass = Class.forName("com.cz.classloader.Test1");
                    Object o = aClass.newInstance();
                    Method test = aClass.getMethod("test", new Class[]{String.class});
                    System.out.println(test.invoke(o, "cz"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        Thread.yield();
    }
}
