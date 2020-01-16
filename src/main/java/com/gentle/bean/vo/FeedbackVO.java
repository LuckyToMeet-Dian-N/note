package com.gentle.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Gentle
 * @date 2020/01/15 : 23:55
 */
@Data
public class FeedbackVO {
    private Integer id;

    private String message;

    private String reply;

    private Boolean isReply;

    private Integer userId;

    private String  userName;

    private Date createTime;

}
