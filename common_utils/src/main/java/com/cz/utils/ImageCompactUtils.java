package com.cz.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author jzm
 * @date 2021/11/30 : 2:46 下午
 */
public class ImageCompactUtils {

    public static void compactImage(String src, String dest) throws IOException {
        File srcFile = new File(src);
        // 检查图片是否存在
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Src image not exists");
        }
        int res[] = getImgWidthHeight(srcFile);
        int width = res[0];
        int height = res[1];
        if (width == 0 || height == 0) {
            throw new IllegalArgumentException("图片宽度或者高度错误");
        }
        // 开始读取文件并进行压缩
        Image srcImg = ImageIO.read(srcFile);

        // 构造一个类型为预定义图像类型之一的BufferedImage
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 设置压缩模式
        tag.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

        // 创建文件输出流
        FileOutputStream fos = new FileOutputStream(dest);

        // 下面的代码和jdk所在的平台相关
        // JDK7起 开始移除了许多com.sun.*中的类
        // 下面的代码在windows jdk8中也许可以使用，但是linux和macos平台不能使用
        // 将图片按JPEG压缩，保存到文件中
//            com.sun.image.codec.jpeg.JPEGImageEncode encode = JPEGEncode.createJPEGEncode(fos);
        // encode.encode(tag);
        // fos.close();
    }

    /**
     * 获取图片的宽度和高度
     *
     * @param file
     * @return res[0]:宽度 res[1]:高度
     */
    private static int[] getImgWidthHeight(File file) throws IOException {
        InputStream is = null;
        BufferedImage bi = null;
        int res[] = {0, 0};
        // 获取文件流
        is = new FileInputStream(file);
        // 将图片写入缓冲区
        bi = ImageIO.read(is);
        // 获取图片宽度
        res[0] = bi.getWidth(null);
        // 获取图片高度
        res[1] = bi.getHeight(null);
        is.close();

        return res;
    }

}
