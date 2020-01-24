package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Gentle
 * @date 2020/01/24 : 17:14
 */
@Data
public class Files {
    @Id
    private Integer id;

    private String noteList;

    private String name;

    private Integer usersId;

    private Date createTime;

}
