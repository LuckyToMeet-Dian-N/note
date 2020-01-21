package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author Gentle
 * @date 2020/01/21 : 19:13
 */
@Data
public class LabelAndNote {

    @Id
    private Integer id;

    private Integer userId;

    private Integer labelId;

    private Integer noteId;



}
