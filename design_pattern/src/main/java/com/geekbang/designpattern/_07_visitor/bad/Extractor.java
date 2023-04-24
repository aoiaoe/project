package com.geekbang.designpattern._07_visitor.bad;

import com.geekbang.designpattern._07_visitor.bad.resouce.PPTFile;
import com.geekbang.designpattern._07_visitor.bad.resouce.PdfFile;
import com.geekbang.designpattern._07_visitor.bad.resouce.WordFile;

public class Extractor {
    public void extract2txt(PPTFile pptFile) {
//...
        System.out.println("Extract PPT.");
    }

    public void extract2txt(PdfFile pdfFile) {
//...
        System.out.println("Extract PDF.");
    }

    public void extract2txt(WordFile wordFile) {
//...
        System.out.println("Extract WORD.");
    }
}