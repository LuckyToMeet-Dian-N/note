package com.gentle.service;

import com.gentle.bean.po.Users;
import com.gentle.bean.vo.UsersVO;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/20 : 14:29
 */
public interface UserInfoService {
    List<Users> listUsers();

    String uodatePassword(String passwrod,int userId);

    String updateUserInfo(Users users);

}
