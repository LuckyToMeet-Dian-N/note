package com.gentle.controller.user;

import com.gentle.bean.po.Label;
import com.gentle.bean.po.LabelAndNote;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.LabelAndNoteMapper;
import com.gentle.mapper.LabelMapper;
import com.gentle.mapper.NoteMapper;
import com.gentle.result.ResultBean;
import com.gentle.service.LabelService;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 14:49
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/users/")
public class LabelController {

    @Resource
    LabelMapper labelMapper;
    @Resource
    LabelAndNoteMapper labelAndNoteMapper;

    @PostMapping(value = "insertLabel")
    public ResultBean<String> insert(Label label) {
        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");

        ValidataUtils.isNotNullByString(label.getLabelName(),"标签名不能为空");
        label.setUpdateTime(new Date());
        label.setCreateTime(new Date());
        label.setUsersId(users.getId());
        labelMapper.insert(label);
        return new ResultBean<>();
    }

    @PostMapping(value = "deleteLabel")
    public ResultBean<String> delete(int id) {
        labelMapper.deleteLabelById(id);
        LabelAndNote labelAndNote = new LabelAndNote();
        labelAndNote.setLabelId(id);
        labelAndNoteMapper.delete(labelAndNote);
        return new ResultBean<>();
    }
    @PostMapping("updateLabel")
    public ResultBean<String> update(Label label) {
        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        label.setId(users.getId());
        labelMapper.updateByPrimaryKeySelective(label);
        return new ResultBean<>();
    }

    @GetMapping("selectLabel")
    public ResultBean<List<Label>> select() {
        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Label label = new Label();
        label.setUsersId(users.getId());
        List<Label> select = labelMapper.select(label);
        return new ResultBean<>(select);
    }
    @Resource
    NoteMapper noteMapper;
    @GetMapping("selectNoteByLabelId")
    public ResultBean<List<Note>> selectByNote(Integer labelId) {
        LabelAndNote labelAndNotel = new LabelAndNote();
        labelAndNotel.setLabelId(labelId);
        List<LabelAndNote> select = labelAndNoteMapper.select(labelAndNotel);
        List<Note> list = new ArrayList<>();
        select.forEach(e->{
            Note note = noteMapper.selectByPrimaryKey(e.getNoteId());
            list.add(note);
        });
        return new ResultBean<>(list);
    }

    @PostMapping("addLabelByNote")
    public ResultBean<String> addLabelByNote(String notes,Integer labelId){
        ValidataUtils.isNotNullByString(notes,"关联笔记不能为空");
        ValidataUtils.isNotNullByString(labelId,"关联笔记 labelId 不能为空");
        Label label = labelMapper.selectByPrimaryKey(labelId);
        ValidataUtils.isNotNullByString(label," labelId 不存在");
        String[] split = notes.split(",");
        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");

       for (String e:split){
            LabelAndNote labelAndNote = new LabelAndNote();
            labelAndNote.setLabelId(labelId);
            labelAndNote.setNoteId(Integer.valueOf(e));
            LabelAndNote select = labelAndNoteMapper.selectOne(labelAndNote);
            if (select!=null){
                throw new CheckException("已经为该笔记添加 "+ label.getLabelName()+" 标签，无法重复添加");
            }
        }
        Arrays.stream(split).forEach(e->{
            LabelAndNote labelAndNote = new LabelAndNote();
            labelAndNote.setLabelId(labelId);
            labelAndNote.setUserId(users.getId());
            labelAndNote.setNoteId(Integer.valueOf(e));
            labelAndNoteMapper.insert(labelAndNote);
        });
        return new ResultBean<>();
    }
    @PostMapping("deleteLabelByNote")
    public ResultBean<String> deleteNoteAndLabel(Integer noteId,Integer labelId){
        ValidataUtils.isNotNullByString(noteId,"关联笔记不能为空");
        ValidataUtils.isNotNullByString(labelId,"关联笔记 labelId 不能为空");
        LabelAndNote labelAndNote = new LabelAndNote();
        labelAndNote.setLabelId(labelId);
        labelAndNote.setNoteId(noteId);
        labelAndNoteMapper.delete(labelAndNote);
        return new ResultBean<>();
    }






}
