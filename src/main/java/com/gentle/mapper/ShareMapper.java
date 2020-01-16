package com.gentle.mapper;

import com.gentle.bean.po.Share;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 00:00
 */
public interface ShareMapper extends BaseMapper<Share> {
    @Select("select count(*) as sheets from share group by date_format(create_time,'%Y-%m') limit 5 ")
    List<Integer> selectTimebyMon();
}
