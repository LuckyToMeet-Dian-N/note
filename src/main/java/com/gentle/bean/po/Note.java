package com.gentle.bean.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Note {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String noteTitle;

    private Integer noteType;

    private Integer usersId;

    private String filePath;

    private Date createTime;

    private Date updateTime;

    private String noteContent;

}