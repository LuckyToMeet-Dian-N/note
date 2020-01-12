package com.gentle.service.impl;

import com.gentle.bean.po.Users;
import com.gentle.bean.vo.UsersVO;
import com.gentle.mapper.OpenUsersMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.service.OpenService;
import com.gentle.service.UserInfoService;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/20 : 14:29
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    OpenUsersMapper openUsersMapper;

    @Override
    public List<Users> listUsers() {
        return userInfoMapper.listUserInfo();
    }

    @Override
    public String uodatePassword(String password, int userId) {
        userInfoMapper.updatePassword(password,userId);

        return "修改成功";
    }

    @Override
    public String  updateUserInfo(Users users) {
        ValidataUtils.isNotNullByString(users.getRegion(),"地区不能为空");
        ValidataUtils.isNotNull(users.getUserName(),"用户名不能为空");
        ValidataUtils.isNotNull(users.getAge(),"年龄不能为空");

        int i = openUsersMapper.updateByPrimaryKeySelective(users);
        return "修改成功";
    }


}
