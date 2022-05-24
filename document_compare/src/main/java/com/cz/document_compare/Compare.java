package com.cz.document_compare;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * @author jzm
 * @date 2022/4/25 : 10:51
 */
public class Compare {

    public static void main(String[] args) throws Exception {
        //原始文件
        List<String> original = Files.readAllLines(new File("/Users/sephiroth/IdeaProjects/project/document_compare/doc/test1.docx").toPath());
        //对比文件
        List<String> revised = Files.readAllLines(new File("/Users/sephiroth/IdeaProjects/project/document_compare/doc/test3.docx").toPath());

        //两文件的不同点
        Patch<String> patch = DiffUtils.diff(original, revised);
        for (AbstractDelta<String> delta : patch.getDeltas()) {
            System.out.println(delta);
        }

    }
}
