package com.gentle.service;

import com.gentle.bean.po.Label;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 14:54
 */
public interface LabelService {

    String insert(Label label);

    String update(Label label);

    String delete(int labelId);

    List<Label> listLabel(int userId);

}
