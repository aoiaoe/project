//package com.cz.poi;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class FileParsingUtil {
//
//    public static void main(String[] args) {
//        File file = new File("/Users/sephiroth/IdeaProjects/project/document_compare/doc/test1.docx");
//        Map<String, String> fileNameAndContent = getFileNameAndContent(file);
//        for (Map.Entry<String, String> entry : fileNameAndContent.entrySet()) {
//            System.out.println(entry.getKey() + "  : " + entry.getValue());
//        }
//    }
//
//    public static Map<String, String> getFileNameAndContent(File file) {
//        String title = "";
//        String content = "";
//        Map<String, String> map = new LinkedHashMap();
//        try {
//            InputStream inputStream = new FileInputStream(file);
//            String fileName = file.getName();
//            if (!fileName.endsWith("doc") && !fileName.endsWith("docx")) {
//                return map;
//            }
//            if (fileName.endsWith("doc")) {
//                WordExtractor xwpfWordExtractor = new WordExtractor(inputStream);
//                String[] paragraphText = xwpfWordExtractor.getParagraphText();
//                title = paragraphText[0].trim();
//                for (int i = 1; i < paragraphText.length; i++) {
//                    content = content + paragraphText[i].trim();
//                }
//            }
//            if (fileName.endsWith("docx")) {
////                XWPFDocument doc = new XWPFDocument(inputStream);
////                List<XWPFParagraph> paras=doc.getParagraphs();
////                title = paras.get(0).getText().trim();
////                for (int i = 1; i < paras.size(); i++) {
////                    content = content + paras.get(i).getText().trim();
////                }
//            }
//            map.put("title", title);
//            map.put("content", content);
//            log.error("word文档解析成功");
//        } catch (Exception e) {
//            e.getMessage();
//            log.error("word文档解析失败");
//        }
//        return map;
//    }
//}