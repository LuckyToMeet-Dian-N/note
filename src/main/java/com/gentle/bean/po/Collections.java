package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class Collections {
    @Id
    private Integer id;

    private Integer noteId;

    private Integer usersId;

    private Date createTime;

    private Date updateTime;


}