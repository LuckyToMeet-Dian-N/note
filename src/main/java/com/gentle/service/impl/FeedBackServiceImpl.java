package com.gentle.service.impl;

import com.gentle.bean.po.Feedback;
import com.gentle.bean.vo.FeedbackVO;
import com.gentle.mapper.FeedbackMapper;
import com.gentle.service.FeedBackService;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 22:41
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public void insertFeedBack(Feedback feedback) {
        ValidataUtils.isNotNullByString(feedback.getMessage(),"消息不能为空");
        feedback.setCreateTime(new Date());
        feedback.setIsReply(false);
        feedbackMapper.insert(feedback);
    }

    @Override
    public void updateFeedBack(Feedback feedback) {
        ValidataUtils.isNotNullByString(feedback.getMessage(),"消息不能为空");
        ValidataUtils.isNotNullByString(feedback.getUserId(),"userId不能为空");
        feedback.setIsReply(true);
        feedback.setReply(feedback.getReply());
        feedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    @Override
    public List<FeedbackVO> selectFeedBack(Integer userId, Boolean isReply) {
        return null;
    }
}
