package com.gentle.controller.user;

import com.gentle.aop.LoginAuthorization;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultBean<String> updatePassword(String newPassword) {
        ValidataUtils.isNotNullByString(newPassword,"新密码不能为空");
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        if (users.getPassword().equals(newPassword)){
            throw new CheckException("密码不能和原来密码一致");
        }
        users.setPassword(newPassword);
        userInfoMapper.updatePassword(newPassword,users.getId());
        return new ResultBean<>();
    }
    @PostMapping(value = "updateUserInfo")
    public ResultBean<String> updateUserInfo(Users users) {
        ValidataUtils.isNotNullByString(users.getUserName(),"用户名不能为空");
        ValidataUtils.isNotNull(users.getId(),"用户id不能为空");
        ValidataUtils.isNotNull(users.getRegion(),"用户地区不能为空");
         userInfoMapper.updateByPrimaryKeySelective(users);
        Users users1 = userInfoMapper.selectByPrimaryKey(users.getId());
        LoginAuthorization.map.put(users1.getId()+"",users1);
        return new ResultBean<>();
    }

    @GetMapping(value = "getUserInfo")
    public ResultBean<Users> getUserInfo() {
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        users.setPassword(null);
        return new ResultBean<>(users);
    }

}
