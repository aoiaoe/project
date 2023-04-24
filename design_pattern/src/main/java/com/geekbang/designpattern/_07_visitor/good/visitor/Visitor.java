package com.geekbang.designpattern._07_visitor.good.visitor;

import com.geekbang.designpattern._07_visitor.good.resource.PPTFile;
import com.geekbang.designpattern._07_visitor.good.resource.PdfFile;
import com.geekbang.designpattern._07_visitor.good.resource.WordFile;

public interface Visitor {
    void visit(PdfFile pdfFile);

    void visit(PPTFile pdfFile);

    void visit(WordFile pdfFile);
}