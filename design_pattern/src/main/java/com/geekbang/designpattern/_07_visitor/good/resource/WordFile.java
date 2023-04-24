package com.geekbang.designpattern._07_visitor.good.resource;

import com.geekbang.designpattern._07_visitor.good.visitor.Visitor;

/**
 * @author jzm
 * @date 2023/4/24 : 17:45
 */
public class WordFile extends ResourceFile {
    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}