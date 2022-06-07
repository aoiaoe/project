package com.cz.file;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * @author jzm
 * @date 2022/5/26 : 17:46
 */
public class FileDemo {
    public static void main(String[] args) {
        // hutool自动去掉file协议头
        System.out.println(FileUtil.exist("file:///Users/sephiroth/arthas-boot.jar"));
        System.out.println(new File("file:///Users/sephiroth/arthas-boot.jar").exists());
    }
}
