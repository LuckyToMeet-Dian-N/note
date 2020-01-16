package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Gentle
 * @date 2019/09/18 : 10:39
 */
@Data
public class Admins {
    @Id
    private Integer id;

    private String adminName;

    private String password;

    private Date createTime;

    private Date updateTime;



}
