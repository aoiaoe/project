package com.cz.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.List;

/**
 * @author jzm
 * @date 2022/9/22 : 17:40
 */
public class HutoolExcel {

    public static void main(String[] args) {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2);
        List<List<String>> rows2 = CollUtil.newArrayList(row3, row4, row5);

        String path = "/Users/sephiroth/IdeaProjects/project/java_base/src/main/java/com/cz/excel/HutoolExcel.xlsx";
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(path,"第一页");
        write(writer, rows);
        writer.setSheet("第二页");
        write(writer, rows2);
        writer.close();
    }

    public static void write(ExcelWriter writer, List<List<String>> rows){
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(rows.size() - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //关闭writer，释放内存
    }
}
