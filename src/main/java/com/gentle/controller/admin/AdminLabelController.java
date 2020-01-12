package com.gentle.controller.admin;

import com.gentle.bean.po.Label;
import com.gentle.result.ResultBean;
import com.gentle.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 14:49
 */
@RestController
@RequestMapping(value = "/api/admin/")
public class AdminLabelController {
    @Autowired
    LabelService labelService;
    @PostMapping(value = "insertLabel")
    public ResultBean<String> insert(Label label) {
        return new ResultBean<>(labelService.insert(label));
    }

    @PostMapping(value = "deleteLabel")
    public ResultBean<String> delete(int labelId) {

        return new ResultBean<>(labelService.delete(labelId));
    }
    @PostMapping(value = "updateLabel")
    public ResultBean<String> update( Label label) {

        return new ResultBean<>(labelService.update(label));
    }

    @GetMapping(value = "selectLabel")
    public ResultBean<List<Label>> select(int userId) {

        return new ResultBean<>(labelService.listLabel(userId));
    }


}
