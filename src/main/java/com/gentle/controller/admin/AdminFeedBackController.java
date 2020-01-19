package com.gentle.controller.admin;

import com.gentle.bean.po.Feedback;
import com.gentle.bean.vo.FeedbackVO;
import com.gentle.mapper.FeedbackMapper;
import com.gentle.result.ResultBean;
import com.gentle.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 22:40
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class AdminFeedBackController {

    @Resource
    FeedBackService feedBackService;
    @Resource
    private FeedbackMapper feedbackMapper;
    @PostMapping(value = "deleteFeedBacK")
    public ResultBean<String> delete(Integer id) {
        feedbackMapper.deleteByPrimaryKey(id);
        return new ResultBean<>();
    }
    @PostMapping("updateFeedBacK")
    public ResultBean<String> update(Feedback feedback) {
        feedBackService.updateFeedBack(feedback);
        return new ResultBean<>();
    }

    @GetMapping(value = "selectFeedBack")
    public ResultBean<List<FeedbackVO>> select(@RequestParam(value = "isReply",required = false) Boolean isReply) {
        System.out.println(isReply);
        List<FeedbackVO> feedbackVOS = feedBackService.selectFeedBack(null, isReply);
        return new ResultBean<>(feedbackVOS);
    }






}
