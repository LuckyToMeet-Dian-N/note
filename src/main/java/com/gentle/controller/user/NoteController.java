package com.gentle.controller.user;

import com.gentle.bean.po.LabelAndNote;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.LabelAndNoteMapper;
import com.gentle.mapper.NoteMapper;
import com.gentle.result.ResultBean;
import com.gentle.service.NoteService;
import com.gentle.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/18 : 18:39
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")
public class NoteController {

    @Resource
    NoteMapper noteMapper;
    @Resource
    LabelAndNoteMapper labelAndNoteMapper;

    @PostMapping(value = "createNote")
    public ResultBean<String> insertNote(Note note) {
        ValidataUtils.isNotNullByString(note.getNoteTitle(),"笔记标题不能为空");
        ValidataUtils.isNotNullByString(note.getNoteContent(),"笔记内容不能为空");
        ValidataUtils.isNotNull(note.getNoteType(),"笔记类型不能为空");
        note.setUpdateTime(new Date());
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");

        note.setUsersId(users.getId());
        note.setCreateTime(new Date());
        noteMapper.insert(note);
        return new ResultBean<>();
    }

    @PostMapping("deleteNote")
    public ResultBean<String> delete(Integer id) {
        noteMapper.deleteByPrimaryKey(id);
        LabelAndNote labelAndNote = new LabelAndNote();
        labelAndNote.setNoteId(id);
        labelAndNoteMapper.delete(labelAndNote);
        return new ResultBean<>();
    }

    @PostMapping("updateNote")
    public ResultBean<String> update(Note note) {
        ValidataUtils.isNotNullByString(note.getId(),"笔记 id 不能为空");
        ValidataUtils.isNotNullByString(note.getNoteTitle(),"笔记标题不能为空");
        ValidataUtils.isNotNullByString(note.getNoteContent(),"笔记内容不能为空");
        ValidataUtils.isNotNull(note.getNoteType(),"笔记类型不能为空");
        noteMapper.updateByPrimaryKeySelective(note);
        return new ResultBean<>();
    }

    @GetMapping("selectNote")
    public ResultBean<List<Note>> select(@RequestParam(value = "noteType" ,required = false) Integer noteType) {
        List<Note> notes;
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");

        if (StringUtils.isEmpty(noteType)){
            Note note = new Note();
            note.setUsersId(users.getId());
            notes = noteMapper.selectAll();
        }else {
            Note note = new Note();
            note.setNoteType(noteType);
            notes= noteMapper.select(note);
        }
        return new ResultBean<>(notes);
    }

    public static boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }
//
    /**
     * 笔记转邮件。
     */
    @PostMapping("sendMail")
    public ResultBean<String> sendEmail(Integer noteId, String recAddr) {

        if (StringUtils.isEmpty(recAddr)) {
            throw new CheckException("邮件地址不能为空");
        }
        if (!checkEmail(recAddr)) {
            throw new CheckException("邮箱格式不正确！");
        }
        Note note = noteMapper.selectByPrimaryKey(noteId);
        ValidataUtils.isNotNull(note,"noteId 不存在");
        try {
            MailUtils.sendEmail(note.getNoteTitle(), note.getNoteContent(), recAddr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CheckException("发送失败，亲稍后重试");
        }
        return new ResultBean<>("发送成功！");
    }

}
