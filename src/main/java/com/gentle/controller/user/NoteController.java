package com.gentle.controller.user;

import com.gentle.bean.po.Note;
import com.gentle.exception.CheckException;
import com.gentle.result.ResultBean;
import com.gentle.service.NoteService;
import com.gentle.utils.FileUpload;
import com.gentle.utils.MailUtils;
import com.gentle.utils.WordUtils1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Gentle
 * @date 2019/09/18 : 18:39
 */
@RestController
@RequestMapping(value = "/users/")
public class NoteController {

//    @Autowired
//    NoteService noteService;
//
//    @PostMapping(value = "createNote")
//    public ResultBean<String> insertNote(Note note) {
//
//        if (StringUtils.isEmpty(note.getNoteTitle())) {
//            throw new CheckException("笔记标题不能为空");
//        }
//        if (StringUtils.isEmpty(note.getFilePath())) {
//            throw new CheckException("笔记路径不能为空");
//        }
//        if (StringUtils.isEmpty(note.getNoteContent())) {
//            throw new CheckException("笔记内容不能为空");
//        }
//        if (StringUtils.isEmpty(note.getNoteType())) {
//            throw new CheckException("笔记类型不能为空");
//        }
//        return new ResultBean<>();
//    }
//
//    @PostMapping()
//    public ResultBean<String> delete() {
//
//        return new ResultBean<>();
//    }
//
//    @PostMapping()
//    public ResultBean<String> update() {
//
//        return new ResultBean<>();
//    }
//
//    @GetMapping()
//    public ResultBean<String> select() {
//
//        return new ResultBean<>();
//    }
//
//    public static boolean checkEmail(String email) {
//        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
//    }
//
//    /**
//     * 笔记转邮件。
//     */
//    @PostMapping()
//    public ResultBean<String> sendEmail(Integer noteId, String recAddr) {
//
//        if (StringUtils.isEmpty(recAddr)) {
//            throw new CheckException("邮件地址不能为空");
//        }
//        if (!checkEmail(recAddr)) {
//            throw new CheckException("邮箱格式不正确！");
//        }
//
//        Note note = new Note();
//
//        try {
//            MailUtils.sendEmail(note.getNoteTitle(), note.getNoteContent(), recAddr);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CheckException("发送失败，亲稍后重试");
//        }
//
//        return new ResultBean<>("发送成功！");
//    }
//
//    /**
//     * word 转笔记。
//     */
//    @PostMapping()
//    public ResultBean<String> wordToNote(MultipartFile file) {
//
//        String originalFilename = file.getOriginalFilename();
//        if (!originalFilename.endsWith("docx")||!originalFilename.endsWith("doc")){
//            throw new CheckException("文件不支持");
//        }
//
//        String s = FileUpload.fileUp(file, "/home/test/", originalFilename);
//
//        String s1 = WordUtils1.readDataDocx(s);
//        Note note  =new Note();
//        note.setNoteTitle(originalFilename.substring(0,originalFilename.indexOf(".")));
//        note.setNoteContent(s);
//
//
//        File file1 = new File("/home/test/", originalFilename);
//        boolean delete = file1.delete();
//        return new ResultBean<>();
//    }
//

 

}
