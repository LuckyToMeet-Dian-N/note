package com.gentle.controller.admin;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.UsersVO;
import com.gentle.exception.CheckException;
import com.gentle.mapper.OpenUsersMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;

import com.gentle.service.OpenService;
import com.gentle.service.UserInfoService;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class AdminUserInfoController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping(value = "updateUserInfo")
    public ResultBean<String> updateUserInfo(Users users) {
        System.out.println(users);
        return new ResultBean<>(userInfoService.updateUserInfo(users));
    }
    @GetMapping(value = "listUsers")
    public ResultBean<List<Users>> listUsers() {
        System.out.println("---");
        return new ResultBean<>(userInfoService.listUsers());
    }
    @PostMapping(value = "selectUsers")
    public ResultBean<String> selectUsers(int userId) {

        return new ResultBean<>();
    }

    @PostMapping(value = "deleteUsers")
    public ResultBean<Integer> deleteUsers(Integer userId) {
        return new ResultBean<>(userInfoMapper.deleteByPrimaryKey(userId));
    }

    @Autowired
    OpenUsersMapper openUsersMapper;
    @PostMapping("addUsers")
    public ResultBean<String> addUsers(Users users) {
        ValidataUtils.isNotNullByString(users.getRegion(),"地区不能为空");
        ValidataUtils.isNotNull(users.getUserName(),"用户名不能为空");
        ValidataUtils.isNotNull(users.getAge(),"年龄不能为空");
        ValidataUtils.isNotNull(users.getEmail(),"年龄不能为空");
        ValidataUtils.isNotNull(users.getPassword(),"密码不能为空");

        Users users1 = openUsersMapper.selectUserInfoByEmail(users.getEmail());
        if (users1!=null){
            throw new CheckException("eamil已经存在");
        }
        users.setUserType(0);
        users.setCreateTime(new Date());
        users.setUpdateTime(new Date());
        users.setBalances(0);
        userInfoMapper.insertSelective(users);
        return new ResultBean<>();
    }

}