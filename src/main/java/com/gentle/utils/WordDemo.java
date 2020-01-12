package com.gentle.utils;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.gentle.exception.CheckException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * 创建word文档 步骤:
 * 1,建立文档
 * 2,创建一个书写器
 * 3,打开文档
 * 4,向文档中写入数据
 * 5,关闭文档
 */
public class WordDemo {

    public WordDemo() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws DocumentException, IOException {
        // 创建word文档,并设置纸张的大小
//        Document document = new Document(PageSize.A4);
//        try {
//            RtfWriter2.getInstance(document,
//                    new FileOutputStream("G:/word.docx"));
//            document.open();
//            //设置合同头
//            Paragraph ph = new Paragraph();
//            Font f = new Font();
//
//            Paragraph p = new Paragraph("出口合同",
//                    new Font(Font.NORMAL, 18, Font.BOLDITALIC, new Color(0, 0, 0)));
//            p.setAlignment(1);
//            document.add(p);
//            ph.setFont(f);
//            Paragraph context = new Paragraph("neirong12313414");
//
//            //段间距
//            context.setSpacingBefore(3);
//            //设置第一行空的列数
//            context.setFirstLineIndent(20);
//            document.add(context);
//
//            document.addSubject("12313131");
//            document.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            throw new CheckException("文件异常，请稍后重试！");
//        }
    }

//    public static void contentToWord(String title,String content,String path) {
//
//// 创建word文档,并设置纸张的大小
//        Document document = new Document(PageSize.A4);
//        try {
//            RtfWriter2.getInstance(document,
//                    new FileOutputStream(path));
//            document.open();
//            //设置合同头
//            Paragraph ph = new Paragraph();
//            Font f = new Font();
//
//            Paragraph p = new Paragraph(title,
//                    new Font(Font.NORMAL, 18, Font.BOLDITALIC, new Color(0, 0, 0)));
//            p.setAlignment(1);
//            document.add(p);
//            ph.setFont(f);
//            Paragraph context = new Paragraph(content);
//
//            //段间距
//            context.setSpacingBefore(3);
//            //设置第一行空的列数
//            context.setFirstLineIndent(20);
//            document.add(context);
//
//            document.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            throw new CheckException("文件异常，请稍后重试！");
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//    }


}