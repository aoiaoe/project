package com.geekbang.designpattern._07_visitor.bad.resouce;

import com.geekbang.designpattern._07_visitor.bad.Extractor;

public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Extractor extractor) {
        extractor.extract2txt(this);
    }
}