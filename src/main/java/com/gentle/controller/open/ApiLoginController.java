package com.gentle.controller.open;

import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.result.ResultBean;
import com.gentle.service.OpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class ApiLoginController {


    @Autowired
    OpenService openService;

    @PostMapping(value = "/users/login")
    public ResultBean<String> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String pwd) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(pwd)) {
            throw new CheckException("用户名或密码不能为空");
        }
        return new ResultBean<>(openService.userLogin(email, pwd));
    }


    @PostMapping(value = "/admin/login")
    public ResultBean<String> adminLogin(@RequestParam(value = "adminName") String number, @RequestParam(value = "password") String pwd) {
        if (StringUtils.isEmpty(number) || StringUtils.isEmpty(pwd)) {
            throw new CheckException("用户名或密码不能为空");
        }

        return new ResultBean<>(openService.adminLogin(number, pwd));
    }

    @PostMapping(value = "/users/register")
    public ResultBean<String> register(Users users,String rePassword) {
        if (StringUtils.isEmpty(users.getPassword()) || StringUtils.isEmpty(users.getEmail())) {
            throw new CheckException("用户名或密码不能为空");
        }
        if (!rePassword.equals(users.getPassword())){
            throw new CheckException("两次密码不一致");
        }
        if (StringUtils.isEmpty(users.getUserName())){
            throw new CheckException("用户名不能为空");
        }
        return new ResultBean<>(openService.register(users));
    }




}