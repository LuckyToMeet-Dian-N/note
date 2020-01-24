package com.gentle.bean.vo;

import com.gentle.bean.po.Note;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/24 : 17:15
 */
@Data
public class FilesVO {
    @Id
    private Integer id;

    private List<Note> noteList;

    private String name;

    private Integer usersId;

    private Date createTime;

    private Note note;
}
