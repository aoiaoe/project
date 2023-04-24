package com.geekbang.designpattern._05_stratagy;

/**
 * @author jzm
 * @date 2023/4/24 : 09:59
 */
public interface ICacheStrategy {

    void save(String key, Object obj);

    void read(String key, Object obj);
}
