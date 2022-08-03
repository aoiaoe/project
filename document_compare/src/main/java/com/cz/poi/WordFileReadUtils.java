package com.cz.poi;

import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;

/**
 * @author jzm
 * @date 2022/6/8 : 11:21
 */
public class WordFileReadUtils {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/sephiroth/IdeaProjects/project/document_compare/doc/test1.doc");
        InputStream inputStream = new FileInputStream(file);
        WordExtractor wordExtractor = new WordExtractor(inputStream);


    }
}
