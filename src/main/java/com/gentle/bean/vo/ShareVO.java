package com.gentle.bean.vo;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Gentle
 * @date 2020/01/15 : 23:52
 */
@Data
public class ShareVO {
    @Id
    private Integer id;

    private Integer noteId;

    private Date createTime;

    private Date updateTime;

    private String noteTitle;

    private String filePath;

    private String  userName;

    private Integer noteType;

    private Integer userId;

    private String noteContent;


}
