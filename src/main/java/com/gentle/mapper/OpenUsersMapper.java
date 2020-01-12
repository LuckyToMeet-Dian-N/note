package com.gentle.mapper;

import com.gentle.bean.po.Admins;
import com.gentle.bean.po.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author Gentle
 * @date 2019/09/17 : 23:42
 */
public interface OpenUsersMapper extends BaseMapper<Users> {
    /**
     *
     * @param email
     * @return
     */
    @Select("select * from users where email = #{email}")
    Users selectUserInfoByEmail(@Param("email") String email);

    @Select("select * from admins where admin_name = #{admin_name}")
    Admins selectAdminInfoByEmail(@Param("admin_name") String email);

    @Insert("insert into users(user_name,email,password) value(#{userName},#{email},#{password})")
    int insertUserInfo(Users users);

//    @Update("update users  " +
//            "set user_name=#{userName},password=#{password}," +
//            "age=#{age},region=#{region} where email=#{email}")
//    int updateUsersInfo(Users users);




}
