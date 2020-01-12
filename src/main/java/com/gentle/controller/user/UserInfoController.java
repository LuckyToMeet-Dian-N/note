package com.gentle.controller.user;

import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.result.ResultBean;
import com.gentle.service.OpenService;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gentle
 * @date 2019/09/18 : 14:38
 */
@RestController
@RequestMapping(value = "/users/")
public class UserInfoController {

//    @Autowired
//    OpenService openService;
//
//    @PostMapping(value = "updatePassword")
//    public ResultBean<String> updatePassword(Users users, String rePassword) {
//
//        if (StringUtils.isEmpty(users.getPassword()) || StringUtils.isEmpty(rePassword)) {
//            throw new CheckException("用户名或密码不能为空");
//        }
//        if (!rePassword.equals(users.getPassword())){
//            throw new CheckException("两次密码不一致");
//        }
//
//        return new ResultBean<>(openService.updateUsersInfo(users));
//    }
//    @PostMapping(value = "updateUserInfo")
//    public ResultBean<String> updateUserInfo(Users users) {
//
//        return new ResultBean<>(openService.updateUsersInfo(users));
//    }

}
