package com.gentle.service.impl;

import com.gentle.bean.po.Feedback;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.FeedbackVO;
import com.gentle.mapper.FeedbackMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.service.FeedBackService;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
        ValidataUtils.isNotNullByString(feedback.getReply(),"消息不能为空");
        ValidataUtils.isNotNullByString(feedback.getUserId(),"userId不能为空");
        feedback.setIsReply(true);
        feedback.setReply(feedback.getReply());
        feedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public List<FeedbackVO> selectFeedBack(Integer userId, Boolean isReply) {
        List<Feedback > list;
        if (StringUtils.isEmpty(userId)){
            if (StringUtils.isEmpty(isReply)){
                list= feedbackMapper.selectAll();
            }else {
                Feedback feedback = new Feedback();
                feedback.setIsReply(isReply);
                list = feedbackMapper.select(feedback);
            }
        }else {
            Feedback feedback = new Feedback();
            feedback.setUserId(userId);
            list = feedbackMapper.select(feedback);
        }
        List<FeedbackVO> feedbackVOS = new ArrayList<>();
        list.forEach(e->{
            FeedbackVO feedbackVO = new FeedbackVO();
            Users users = userInfoMapper.selectByPrimaryKey(e.getUserId());
            BeanUtils.copyProperties(e,feedbackVO);
            feedbackVO.setUserName(users.getUserName());
            feedbackVOS.add(feedbackVO);
        });
        return feedbackVOS;
    }
}
