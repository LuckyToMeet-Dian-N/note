package com.gentle.bean.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Label {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String labelName;

    private Date createTime;

    private Date updateTime;

    private Integer usersId;


}