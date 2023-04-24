package com.geekbang.designpattern._07_visitor.good.resource;

import com.geekbang.designpattern._07_visitor.good.visitor.Visitor;

public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}