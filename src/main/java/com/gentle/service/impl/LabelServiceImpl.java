package com.gentle.service.impl;

import com.gentle.bean.po.Label;
import com.gentle.exception.CheckException;
import com.gentle.mapper.LabelMapper;
import com.gentle.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 14:55
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    LabelMapper labelMapper;


    @Override
    @Transactional
    public String insert(Label label) {
        if (StringUtils.isEmpty(label.getLabelName())){
            throw new CheckException("标签名不能为空");
        }
        labelMapper.insertLabel(label);

        return "新增成功";
    }

    @Override
    @Transactional
    public String update(Label label) {
        if (StringUtils.isEmpty(label.getLabelName())){
            throw new CheckException("标签名不能为空");
        }
        if (StringUtils.isEmpty(label.getId())){
            throw new CheckException("标签ID不能为空");
        }
        labelMapper.updateLabel(label);
        return "修改成功";
    }

    @Override
    @Transactional
    public String delete(int labelId) {
        int i = labelMapper.deleteLabelById(labelId);
        if (i>0){
            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public List<Label> listLabel(int userId) {


        return labelMapper.listLabelByUserId(userId);
    }
}
