package com.gentle.service;

import com.gentle.bean.po.Feedback;
import com.gentle.bean.vo.FeedbackVO;

import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 22:41
 */
public interface FeedBackService {

    void insertFeedBack(Feedback feedback);

    void updateFeedBack(Feedback feedback);

    List<FeedbackVO> selectFeedBack(Integer userId,Boolean isReply);
}
