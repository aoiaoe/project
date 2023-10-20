package com.geekbang.designpattern._07_visitor.good;

import com.geekbang.designpattern._07_visitor.good.resource.PPTFile;
import com.geekbang.designpattern._07_visitor.good.resource.PdfFile;
import com.geekbang.designpattern._07_visitor.good.resource.ResourceFile;
import com.geekbang.designpattern._07_visitor.good.resource.WordFile;
import com.geekbang.designpattern._07_visitor.good.visitor.Compressor;
import com.geekbang.designpattern._07_visitor.good.visitor.ExtractVisitor;
import com.geekbang.designpattern._07_visitor.good.visitor.MetaInfoVisitor;
import com.geekbang.designpattern._07_visitor.good.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jzm
 * @date 2023/4/24 : 18:04
 */
public class ToolApplication {

    public static void main(String[] args) {

        Visitor extractor = new ExtractVisitor();
        Visitor compressor = new Compressor();
        Visitor metaInfoVisitor = new MetaInfoVisitor();
        List<ResourceFile> resourceFiles = listAllResourceFiles(null);
        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(extractor);
            resourceFile.accept(compressor);
            resourceFile.accept(metaInfoVisitor);
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
