package com.gentle.mapper;

import com.gentle.bean.po.Users;
import com.gentle.bean.vo.UsersVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/20 : 14:37
 */
public interface UserInfoMapper extends BaseMapper<Users> {

    @Select("select * from users")
    List<Users> listUserInfo();

    @Update("update users set password = #{password} where id= #{userId}")
    int updatePassword(@Param("password") String password, @Param("userId") int userId);
    @Select("select count(*) as sheets from users group by date_format(create_time,'%Y-%m') limit 5 ")
    List<Integer> selectTimebyMon();

    @SelectProvider(method="queryUsers",type=MySqlProvider.class)
    List<Users> queryUserList(Users users);

}
