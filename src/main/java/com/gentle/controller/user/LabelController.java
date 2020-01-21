package com.gentle.controller.user;

import com.gentle.bean.po.Label;
import com.gentle.bean.po.LabelAndNote;
import com.gentle.bean.po.Users;
import com.gentle.mapper.LabelAndNoteMapper;
import com.gentle.mapper.LabelMapper;
import com.gentle.result.ResultBean;
import com.gentle.service.LabelService;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 14:49
 */
@RestController
@RequestMapping(value = "/users/")
public class LabelController {

    @Resource
    LabelMapper labelMapper;
    @Resource
    LabelAndNoteMapper labelAndNoteMapper;

    @PostMapping(value = "insertLabel")
    public ResultBean<String> insert(Label label) {
        ValidataUtils.isNotNullByString(label.getLabelName(),"标签名不能为空");
        label.setUpdateTime(new Date());
        label.setCreateTime(new Date());
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
        return new ResultBean<>();
    }

    @GetMapping("selectLabel")
    public ResultBean<List<Label>> select() {
        List<Label> labels = labelMapper.selectAll();
        return new ResultBean<>(labels);
    }

}
