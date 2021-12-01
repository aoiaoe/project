package com.cz.utils;

import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author jzm
 * @date 2021/11/30 : 6:01 下午
 */
public class Base64Utils {

    public static String base64(String filePath) throws IOException{
        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        byte data[] = null;
        InputStream is =new FileInputStream(file);
        data = new byte[is.available()];
        is.read(data);
        is.close();
        return new BASE64Encoder().encode(data);
    }

    public static void main(String[] args) throws IOException {
        String s = base64("/Users/sephiroth/Documents/BackgroundImages/DarkLight.jpg");
        File file = new File("/Users/sephiroth/Documents/BackgroundImages/DarkLight.base64");
        OutputStream fos = new FileOutputStream(file);
        fos.write(s.getBytes());
        fos.close();
        System.out.println(s);
    }
}
