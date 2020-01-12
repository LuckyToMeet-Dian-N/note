package com.gentle.utils;

import com.gentle.exception.CheckException;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.document.RtfDocument;
import com.lowagie.text.rtf.text.RtfChunk;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Gentle
 * @date 2019/09/28 : 11:15
 */
public class WordUtils {
    public static void contentToWord(String title, String content, String path) {

        // 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);
        try {
            RtfWriter2.getInstance(document,
                    new FileOutputStream(path));
            document.open();
            //设置合同头
            Paragraph ph = new Paragraph();
            Font f = new Font();

            Paragraph p = new Paragraph(title,
                    new Font(Font.NORMAL, 18, Font.BOLDITALIC, new Color(0, 0, 0)));
            p.setAlignment(1);
            document.add(p);
            ph.setFont(f);
            Paragraph context = new Paragraph(content);

            //段间距
            context.setSpacingBefore(3);
            //设置第一行空的列数
            context.setFirstLineIndent(20);
            document.add(context);

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new CheckException("文件异常，请稍后重试！");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        contentToWord("gentle","123131","g:/word.docx");

        String path = "g:\\";
        String fileName = "word"+".docx";
        String filePath = path + fileName;

        //创建word
        WordUtils1.createWord(path,fileName);
//        //写入数据
        WordUtils1.writeDataDocx(filePath,"124124124125412",true,12);

        //读取数据
        String contentWord=WordUtils1.readDataDocx(filePath);
        System.out.println("word的内容为:\n"+contentWord);
        System.out.println();

    }
}
