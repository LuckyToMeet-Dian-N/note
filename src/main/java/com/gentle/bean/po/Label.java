package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Label {
    @Id
    private Integer id;

    private String labelName;

    private Integer usersId;

    private Date createTime;

    private Date updateTime;


}