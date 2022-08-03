package com.cz.poi;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author jzm
 * @date 2022/6/9 : 10:57
 */
public class PdfBoxDemo {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/sephiroth/IdeaProjects/project/document_compare/doc/test1.pdf");
        FileInputStream is = new FileInputStream(file);
        PDDocument load = PDDocument.load(is);
        String text = new PDFTextStripper().getText(load);
        System.out.println(text);

        load.close();
        is.close();

    }
}
