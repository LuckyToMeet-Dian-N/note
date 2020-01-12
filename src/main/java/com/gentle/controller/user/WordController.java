package com.gentle.controller.user;

import com.gentle.bean.po.Note;
import com.gentle.result.ResultBean;
import com.gentle.utils.FileDownload;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.WordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Gentle
 * @date 2019/09/28 : 10:38
 */
@Controller
public class WordController {

    /**
     * 笔记转word 并下载。
     */
//    @PostMapping()
    public void noteToWord(Integer noteId) {

        Note note = new Note();
        String path = "/home/test/" + note.getId() + ".docx";
        WordUtils.contentToWord(note.getNoteTitle(), note.getNoteContent(), path);

        try {
            FileDownload.fileDownload(RequestAndResponseUtils.getResponse(), path, note.getId() + ".docx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
