package com.gentle.mapper;

import com.gentle.bean.po.Users;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Gentle
 * @date 2020/04/07 : 13:49
 */
public class MySqlProvider {

    public String queryUsers(Users users){
        SQL sql = new SQL().SELECT("*").FROM("users");

        if (!StringUtils.isEmpty(users.getUserName())){
            users.setUserName("%"+users.getUserName()+"%");
            sql.WHERE("user_name like '"+users.getUserName()+"'");
        }
        if (Objects.nonNull(users.getId())){
            sql.WHERE("id="+users.getId());
        }
        if (Objects.nonNull(users.getUserType())){
            sql.WHERE("user_type="+users.getUserType());
        }
        return sql.toString();
    }

    public static void main(String[] args) {

        MySqlProvider mySqlProvider = new MySqlProvider();
        Users users = new Users();
        users.setUserName("g");
        String s = mySqlProvider.queryUsers(users);
        System.out.println(s);

    }
}
