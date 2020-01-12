package com.gentle.mapper;

import com.gentle.bean.po.Label;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/20 : 10:08
 */
public interface LabelMapper extends BaseMapper<Label>{


    @Select("select * from label where user_id =#{userId}")
    List<Label> listLabelByUserId(@Param("userId") int userId);

    @Delete("delete form label where id=#{labelId}")
    int deleteLabelById(@Param("labelId") int labelId);
    @Insert("insert into lable value(null,#{labelName},#{usersId},now(),now())")
    int  insertLabel(Label label);
    @Update("update label set label_name=#{labelName} where id =#{id}")
    void updateLabel(Label label);
}
