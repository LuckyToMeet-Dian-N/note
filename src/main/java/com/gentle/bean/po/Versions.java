package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author Gentle
 * @date 2020/01/15 : 23:49
 */
@Data
public class Versions {

    @Id
    private Integer id;

    private String description;

    private String path;

    private String createTime;

    private  Integer version;
}
