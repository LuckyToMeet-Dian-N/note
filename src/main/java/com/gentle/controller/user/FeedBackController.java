package com.gentle.controller.user;

import com.gentle.bean.po.Feedback;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.mapper.FeedbackMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/21 : 20:33
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")
public class FeedBackController {

    @Resource
    FeedbackMapper feedbackMapper;

    @PostMapping(value = "insertFeedBack")
    public ResultBean<String> insertFeedBack(Feedback feedback) {
        ValidataUtils.isNotNullByString(feedback.getMessage(),"反馈消息不能为空");
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        feedback.setUserId(users.getId());
        feedback.setIsReply(false);
        feedback.setCreateTime(new Date());
        feedbackMapper.insert(feedback);
        return new ResultBean<>();
    }

    @GetMapping(value = "selectMyMessage")
    public ResultBean<List<Feedback>> selectMyMessage() {
        List<Feedback> list = feedbackMapper.selectAll();
        return new ResultBean<>(list);
    }


}
