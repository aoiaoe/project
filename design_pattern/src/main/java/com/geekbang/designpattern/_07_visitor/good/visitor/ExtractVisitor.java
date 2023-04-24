package com.geekbang.designpattern._07_visitor.good.visitor;

import com.geekbang.designpattern._07_visitor.good.resource.PPTFile;
import com.geekbang.designpattern._07_visitor.good.resource.PdfFile;
import com.geekbang.designpattern._07_visitor.good.resource.WordFile;

/**
 * @author jzm
 * @date 2023/4/24 : 18:02
 */
public class ExtractVisitor implements Visitor {
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("抽取PDF");
    }

    @Override
    public void visit(PPTFile pdfFile) {
        System.out.println("抽取PPT");
    }

    @Override
    public void visit(WordFile pdfFile) {
        System.out.println("抽取WORD");
    }
}
