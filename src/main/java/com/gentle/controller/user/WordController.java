package com.gentle.controller.user;

import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.NoteMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * @author Gentle
 * @date 2019/09/28 : 10:38
 */
@Controller
@CrossOrigin("*")
@RequestMapping(value = "/api/users/")
public class WordController {
    @Resource
    NoteMapper  noteMapper;

    /**
     * 笔记转word 并下载。
     */
    @PostMapping("downloadNote")
    @ResponseBody
    public ResultBean<String> workToNote(@RequestParam("file") MultipartFile file) {
        ValidataUtils.isNotNull(file,"上传文件不能为空");
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        if (!(substring.equals("docx")|| substring.equals("doc"))){
            throw new CheckException("只允许 doc 和 docx 格式文件");
        }
        String s = FileUpload.fileUp(file, FileConstants.DEFAULT_ROOT_PATH, UuidUtil.get32UUID());
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        String contentWord=WordUtils1.readDataDocx(FileConstants.DEFAULT_ROOT_PATH+s);

        Note note = new Note();
        note.setNoteContent(contentWord);
        note.setNoteTitle(s);
        note.setCreateTime(new Date());
        note.setNoteType(0);
        note.setUsersId(users.getId());
        note.setUpdateTime(new Date());
        noteMapper.insert(note);
        return new ResultBean<>();
    }



    /**
     * 笔记转word 并下载。
     */
    @PostMapping("insertNoteByNote")
    public void noteToWord(String noteIds) {
        ValidataUtils.isNotNullByString(noteIds,"noteId 不能为空");
        noteIds = noteIds.trim();
        String[] split = noteIds.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(split).forEach(sss->{
            Note note = noteMapper.selectByPrimaryKey(sss);
            stringBuilder.append(note.getNoteContent());
            stringBuilder.append("\r\r\r");
        });
        Note note = new Note();
        String path = "/home/test/" + note.getId() + ".docx";
        WordUtils.contentToWord(note.getNoteTitle(), stringBuilder.toString(), path);
        try {
            FileDownload.fileDownload(RequestAndResponseUtils.getResponse(), path, note.getId() + ".docx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
