package com.geekbang.designpattern._07_visitor.bad.resouce;

import com.geekbang.designpattern._07_visitor.bad.Extractor;

/**
 * @author jzm
 * @date 2023/4/24 : 17:45
 */
public class WordFile extends ResourceFile {
    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Extractor extractor) {
        extractor.extract2txt(this);
    }
}
