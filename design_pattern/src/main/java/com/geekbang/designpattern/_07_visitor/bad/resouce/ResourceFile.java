package com.geekbang.designpattern._07_visitor.bad.resouce;

import com.geekbang.designpattern._07_visitor.bad.Extractor;

/**
 * @author jzm
 * @date 2023/4/24 : 17:34
 */
public abstract class ResourceFile {

    protected String filePath;
    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }

    abstract public void accept(Extractor extractor);
}
