package com.gentle.service;

import com.gentle.bean.po.Admins;
import com.gentle.bean.po.Users;

/**
 * @author Gentle
 * @date 2019/09/18 : 09:22
 */
public interface OpenService {

    String userLogin(String email,String password);

    String adminLogin(String adminName, String password);

    String register(Users users);

    String updateUsersInfo(Users users);

}
