package com.gentle.controller.user;

import com.gentle.bean.po.Label;
import com.gentle.bean.po.Users;
import com.gentle.result.ResultBean;
import com.gentle.service.LabelService;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 14:49
 */
@RestController
@RequestMapping(value = "/users/")
public class LabelController {

//    @Autowired
//    LabelService labelService;
//
//
//    @PostMapping(value = "insertLabel")
//    public ResultBean<String> insert(Label label) {
//
//        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
//        label.setUsersId(users.getId());
//        return new ResultBean<>(labelService.insert(label));
//    }
//
//    @PostMapping(value = "deleteLabel")
//    public ResultBean<String> delete(int labelId) {
//
//        return new ResultBean<>(labelService.delete(labelId));
//    }
//    @PostMapping("updateLabel")
//    public ResultBean<String> update(Label label) {
//
//        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
//        label.setId(users.getId());
//        return new ResultBean<>(labelService.update(label));
//    }
//
//    @GetMapping("selectLabel")
//    public ResultBean<List<Label>> select() {
//
//        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
//        return new ResultBean<>(labelService.listLabel(users.getId()));
//    }

}
