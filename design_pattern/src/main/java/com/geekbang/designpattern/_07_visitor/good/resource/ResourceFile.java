package com.geekbang.designpattern._07_visitor.good.resource;

import com.geekbang.designpattern._07_visitor.good.visitor.Visitor;

/**
 * @author jzm
 * @date 2023/4/24 : 17:33
 */
public abstract class ResourceFile {
    protected String filePath;
    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }
    abstract public void accept(Visitor vistor);
}
