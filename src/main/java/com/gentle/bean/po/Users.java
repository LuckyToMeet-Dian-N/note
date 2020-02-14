package com.gentle.bean.po;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Data
public class Users {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String userName;

    private String email;

    private String password;

    private Integer age;

    private String region;

    private Date createTime;

    private Date updateTime;

    private Integer userType;

    private Integer balances;

    private String  security;

}