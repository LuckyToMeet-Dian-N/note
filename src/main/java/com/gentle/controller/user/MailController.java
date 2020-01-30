package com.gentle.controller.user;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.NoteMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.MailUtils;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/30 : 16:23
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/users/")
public class MailController {

    @Resource
    private NoteMapper noteMapper;

    @PostMapping(value = "sendEmail")
    public ResultBean<String> insertNote(String eMail,Integer noteId) {
        ValidataUtils.isNotNullByString(eMail,"发送邮箱不能为开孔");
        ValidataUtils.isNotNullByString(noteId,"发送笔记不能为空");
        Note note = noteMapper.selectByPrimaryKey(noteId);
        ValidataUtils.isNotNull(note,"noteId 不存在");
        try {
            if (note.getNoteType()==0){
                MailUtils.sendEmail(note.getNoteTitle(),note.getNoteContent(),eMail);
            }else {
                MailUtils.sendEmail(note.getNoteTitle(),note.getFilePath(),eMail);
            }
        } catch (Exception e) {
            throw new CheckException("发送失败，请稍后重试");
        }
        return new ResultBean<>();
    }
}
