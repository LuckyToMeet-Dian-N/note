package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class Share {
    @Id
    private Integer id;

    private Integer noteId;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    private Integer userId;


}