package com.gentle.controller.admin;

import com.gentle.bean.po.Feedback;
import com.gentle.mapper.FeedbackMapper;
import com.gentle.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Gentle
 * @date 2020/01/16 : 22:40
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class FeedBackController {

    @Resource
    private FeedbackMapper feedbackMapper;
    @PostMapping(value = "deleteFeedBacK")
    public ResultBean<String> delete(Integer id) {
        feedbackMapper.deleteByPrimaryKey(id);
        return new ResultBean<>();
    }
    @PostMapping("updateFeedBacK")
    public ResultBean<String> update(Feedback feedback) {

        return new ResultBean<>();
    }

    @GetMapping(value = "selectFeedBack")
    public ResultBean<String> select(@RequestParam(value = "isReply",required = false) Boolean isReply) {

        return new ResultBean<>();
    }






}
