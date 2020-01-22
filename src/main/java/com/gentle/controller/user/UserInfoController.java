package com.gentle.controller.user;

import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Gentle
 * @date 2019/09/18 : 14:38
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")
public class UserInfoController {

    @Resource
    UserInfoMapper userInfoMapper;

    @PostMapping(value = "updatePassword")
    public ResultBean<String> updatePassword(String password) {
        ValidataUtils.isNotNullByString(password,"新密码不能为空");
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        if (users.getPassword().equals(password)){
            throw new CheckException("密码不能和原来密码一致");
        }
        users.setPassword(password);
        userInfoMapper.updatePassword(password,users.getId());
        return new ResultBean<>();
    }
    @PostMapping(value = "updateUserInfo")
    public ResultBean<String> updateUserInfo(Users users) {
        ValidataUtils.isNotNullByString(users.getUserName(),"用户名不能为空");
        int i = userInfoMapper.updateByPrimaryKeySelective(users);
        return new ResultBean<>();
    }

}
