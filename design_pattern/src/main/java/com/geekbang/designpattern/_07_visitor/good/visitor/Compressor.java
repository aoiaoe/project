package com.geekbang.designpattern._07_visitor.good.visitor;

import com.geekbang.designpattern._07_visitor.good.resource.PPTFile;
import com.geekbang.designpattern._07_visitor.good.resource.PdfFile;
import com.geekbang.designpattern._07_visitor.good.resource.WordFile;

public class Compressor implements Visitor {
    @Override
    public void visit(PPTFile pptFile) {
//...
        System.out.println("Compress PPT.");
    }

    @Override
    public void visit(PdfFile pdfFile) {
//...
        System.out.println("Compress PDF.");
    }

    @Override
    public void visit(WordFile wordFile) {
//...
        System.out.println("Compress WORD.");
    }
}