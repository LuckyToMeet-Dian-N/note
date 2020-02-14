package com.gentle.controller.user;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.FilesMapper;
import com.gentle.mapper.NoteMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

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
    @Resource
    FilesMapper filesMapper;

    /**
     * wrod 转笔记。
     */
    @PostMapping("insertNoteByWord")
    @ResponseBody
    public ResultBean<String> workToNote(@RequestParam("file") MultipartFile file) {
        ValidataUtils.isNotNull(file,"上传文件不能为空");
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        if (!(substring.equals("docx")|| substring.equals("doc"))){
            throw new CheckException("只允许 doc 和 docx 格式文件");
        }
        String uuid = UuidUtil.get32UUID();
        uuid = uuid.substring(uuid.length()-20);
        String s = FileUpload.fileUp(file, FileConstants.DEFAULT_ROOT_PATH, uuid);
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        String contentWord=WordUtils1.readDataDocx(FileConstants.DEFAULT_ROOT_PATH+s);

        Note note = new Note();
        note.setNoteContent(contentWord);
        note.setNoteTitle(uuid);
        note.setCreateTime(new Date());
        note.setNoteType(0);
        note.setUsersId(users.getId());
        note.setUpdateTime(new Date());
        noteMapper.insert(note);
        Files files = new Files();
        files.setUsersId(users.getId());
        List<Files> select = filesMapper.select(files);
        Files files2 = new Files();
        files2.setId(select.get(0).getId());
        files2.setNoteList(select.get(0).getNoteList()+","+note.getId());
        filesMapper.updateByPrimaryKeySelective(files2);
        return new ResultBean<>();
    }



    /**
     * 笔记转word 并下载。
     */
    @GetMapping("downloadNote")
    public void noteToWord(String noteIds) {
        ValidataUtils.isNotNullByString(noteIds,"noteId 不能为空");
        noteIds = noteIds.trim();
        String[] split = noteIds.split(",");
        for (String string:split){
            Note note = noteMapper.selectByPrimaryKey(Integer.valueOf(string));
            ValidataUtils.isNotNull(note,"noteId 不存在");
            if (!note.getNoteType().equals(0)){
                throw new CheckException("不能导出图片或视频笔记");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(split).forEach(sss->{
            Note note = noteMapper.selectByPrimaryKey(sss);
            stringBuilder.append(note.getNoteContent());
            stringBuilder.append("\r\r\r");
        });
        String uuid = UUID.randomUUID().toString();
        String path = "/home/test/" + uuid + ".docx";
        WordUtils1.createWord("/home/test/",uuid + ".docx");
        WordUtils1.writeDataDocx(path,stringBuilder.toString(),false,13);
        try {
            FileDownload.fileDownload(RequestAndResponseUtils.getResponse(), "/home/test/", uuid + ".docx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
