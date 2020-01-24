package com.gentle.bean.vo;

import com.gentle.bean.po.Note;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Gentle
 * @date 2020/01/24 : 11:40
 */
@Data
public class CollectionsVO {

    @Id
    private Integer id;

    private Note note;

    private String userName;

    private Integer usersId;

    private Date createTime;
}
