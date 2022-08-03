package com.cz.openpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author jzm
 * @date 2022/6/9 : 10:40
 */
public class OpenPdfDemo {

    public static void main(String[] args) throws Exception {
       testReader();
    }

    private static void testReader() throws Exception {
        PdfReader pdfReader = new PdfReader("/Users/sephiroth/IdeaProjects/project/document_compare/doc/test1.pdf");
        int numberOfPages = pdfReader.getNumberOfPages();

        PdfTextExtractor pdfTextExtractor = new PdfTextExtractor(pdfReader);
        for (int i = 0; i < numberOfPages; i++) {
            System.out.println(i+1 + " --- ");
            System.out.println(pdfTextExtractor.getTextFromPage(1));
        }

        pdfReader.close();
    }

    public static void testWriter2(){
        System.out.println("the Paragraph object");

        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter.getInstance(document, new FileOutputStream("Paragraphs.pdf"));

            // step 3: we open the document
            document.open();
            // step 4:
            Paragraph p1 = new Paragraph(new Chunk(
                    "This is my first paragraph. ",
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
            p1.add("The leading of this paragraph is calculated automagically. ");
            p1.add("The default leading is 1.5 times the fontsize. ");
            p1.add(new Chunk("You can add chunks "));
            p1.add(new Phrase("or you can add phrases. "));
            p1.add(new Phrase(
                    "Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.",
                    FontFactory.getFont(FontFactory.HELVETICA, 18)));
            document.add(p1);
            Paragraph p2 = new Paragraph(new Phrase(
                    "This is my second paragraph. ", FontFactory.getFont(
                    FontFactory.HELVETICA, 12)));
            p2.add("As you can see, it started on a new line.");
            document.add(p2);
            Paragraph p3 = new Paragraph("This is my third paragraph.",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            document.add(p3);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        // step 5: we close the document
        document.close();
    }

    public static void testWriter(){
        System.out.println("Hello World");

        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfWriter.getInstance(document,
                    new FileOutputStream("HelloWorld.pdf"));

            // step 3: we open the document
            document.open();
            // step 4: we add a paragraph to the document
            document.add(new Paragraph("Hello World"));
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        // step 5: we close the document
        document.close();
    }
}
