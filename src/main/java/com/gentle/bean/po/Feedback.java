package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author Gentle
 * @date 2020/01/15 : 23:47
 */
@Data
public class Feedback {
    @Id
    private Integer id;

    private String message;

    private String reply;

    private Boolean isReply;

    private Integer userId;
}
