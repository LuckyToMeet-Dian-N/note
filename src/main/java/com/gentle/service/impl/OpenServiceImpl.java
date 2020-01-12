package com.gentle.service.impl;

import com.gentle.bean.po.Admins;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.OpenUsersMapper;
import com.gentle.service.OpenService;
import com.gentle.utils.JsonUtil;
import com.gentle.utils.RedisService;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Gentle
 * @date 2019/09/18 : 09:22
 */
@Service
public class OpenServiceImpl implements OpenService {
    @Autowired
    private RedisService<String> redisService;

    @Autowired
    OpenUsersMapper openUsersMapper;

    @Override
    public String userLogin(String email, String password) {

        Users users = openUsersMapper.selectUserInfoByEmail(email);
        if (users == null) {
            throw new CheckException("用户不存在");
        }
        if (!users.getPassword().equals(password)) {
            throw new CheckException("密码不正确");
        }
        String token = UuidUtil.get32UUID();
        redisService.hashSet(token, "users", JsonUtil.objectToJson(users), 60 * 60 * 6);

        return token;
    }

    @Override
    public String adminLogin(String adminName, String password) {
        Admins adminInfo = openUsersMapper.selectAdminInfoByEmail(adminName);
        if (adminInfo == null) {
            throw new CheckException("用户不存在");
        }
        if (!adminInfo.getPassword().equals(password)) {
            throw new CheckException("输入密码不正确");
        }
        String token = UuidUtil.get32UUID();
        redisService.hashSet(token, "admin", JsonUtil.objectToJson(adminInfo), 60 * 60 * 6);

        return token;
    }

    @Override
    public String register(Users users) {
        Users users1 = openUsersMapper.selectUserInfoByEmail(users.getEmail());

        if (users1 != null) {
            throw new CheckException("邮箱已经存在！");
        }

        int i = openUsersMapper.insertUserInfo(users);
        if (i <= 0) {
            throw new CheckException("注册失败！请重试");
        }
        return "注册成功";
    }

    @Override
    public String updateUsersInfo(Users users) {

        openUsersMapper.updateByPrimaryKeySelective(users);

        return "修改成功";
    }
}
