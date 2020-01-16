package com.gentle.mapper;

import com.gentle.bean.po.Note;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/10 : 20:20
 */
public interface NoteMapper extends BaseMapper<Note> {
    @Select("select count(*) as sheets from note group by date_format(create_time,'%Y-%m') limit 5 ")
    List<Integer> selectTimebyMon();

}
