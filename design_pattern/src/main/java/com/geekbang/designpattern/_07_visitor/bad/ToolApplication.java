package com.geekbang.designpattern._07_visitor.bad;

import com.geekbang.designpattern._07_visitor.bad.resouce.PPTFile;
import com.geekbang.designpattern._07_visitor.bad.resouce.PdfFile;
import com.geekbang.designpattern._07_visitor.bad.resouce.ResourceFile;
import com.geekbang.designpattern._07_visitor.bad.resouce.WordFile;

import java.util.ArrayList;
import java.util.List;

public class ToolApplication {
    public static void main(String[] args) {
        Extractor extractor = new Extractor();
        List<ResourceFile> resourceFiles = listAllResourceFiles(args[0]);
        for (ResourceFile resourceFile : resourceFiles) {
            // 编译失败，因为方法重载是在编译时绑定，但是并没有定义一个参数为ResourceFile的方法
            // extractor.extract2txt(resourceFile);
            // 所以在ResourceFile类中增加accept方法, 各文件类型类实现accept，将extractor传入，类似于回调
            resourceFile.accept(extractor);
        }
    }

    private static List<ResourceFile> listAllResourceFiles(String resourceDirectory) {
        List<ResourceFile> resourceFiles = new ArrayList<>();
//...根据后缀(pdf/ppt/word)由工厂方法创建不同的类对象(PdfFile/PPTFile/WordFile)
        resourceFiles.add(new PdfFile("a.pdf"));
        resourceFiles.add(new WordFile("b.word"));
        resourceFiles.add(new PPTFile("c.ppt"));
        return resourceFiles;
    }
}