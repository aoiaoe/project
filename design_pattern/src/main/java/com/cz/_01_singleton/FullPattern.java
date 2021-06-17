package com.cz._01_singleton;

/**
 * 懒汉式
 * 优点：实现懒加载，合理利用系统资源。
 * <p>
 * 缺点：需要添加同步锁，高并发时调用效率不高。
 */
public class FullPattern {

    private FullPattern() {
    }

    private static FullPattern instance = null;

    public static FullPattern getInstance() {
        if (instance == null) {
            instance = new FullPattern();
        }
        return instance;
    }

}
