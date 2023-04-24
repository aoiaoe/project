package com.geekbang.designpattern._07_visitor.good.visitor;

import com.geekbang.designpattern._07_visitor.good.resource.PPTFile;
import com.geekbang.designpattern._07_visitor.good.resource.PdfFile;
import com.geekbang.designpattern._07_visitor.good.resource.WordFile;

/**
 * @author jzm
 * @date 2023/4/24 : 18:12
 */
public class MetaInfoVisitor implements Visitor {
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("PDF元信息");
    }

    @Override
    public void visit(PPTFile pdfFile) {
        System.out.println("PPT元信息");
    }

    @Override
    public void visit(WordFile pdfFile) {
        System.out.println("PDF元信息");
    }
}
