package com.gentle.controller.open;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.FilesMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.service.OpenService;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ApiLoginController {


    @Autowired
    OpenService openService;
    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    FilesMapper filesMapper;

    @PostMapping(value = "/users/login")
    public ResultBean<String> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String pwd) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(pwd)) {
            throw new CheckException("用户名或密码不能为空");
        }
        return new ResultBean<>(openService.userLogin(email, pwd));
    }


    @PostMapping(value = "/admin/login")
    public ResultBean<String> adminLogin(@RequestParam(value = "account") String number, @RequestParam(value = "password") String pwd) {
        if (StringUtils.isEmpty(number) || StringUtils.isEmpty(pwd)) {
            throw new CheckException("用户名或密码不能为空");
        }
        return new ResultBean<>(openService.adminLogin(number, pwd));
    }

    @PostMapping (value = "/users/register")
    public ResultBean<String> register(Users users) {
        ValidataUtils.isNotNullByString(users.getEmail(),"邮箱不能为空");
        if (StringUtils.isEmpty(users.getPassword())) {
            throw new CheckException("密码不能为空");
        }
        if (StringUtils.isEmpty(users.getUserName())){
            throw new CheckException("用户名不能为空");
        }
        ValidataUtils.isNotNullByString(users.getSecurity(),"安全码不能为空");
        Users users1 = new Users();
        users1.setEmail(users.getEmail());
        Users select = userInfoMapper.selectOne(users1);
        if (select!=null){
            throw new CheckException("邮箱已存在，请更换重试");
        }
        users.setBalances(0);
        users.setUserType(0);
        users.setCreateTime(new Date());
        users.setUpdateTime(new Date());
        int i = userInfoMapper.insertSelective(users);

        Files files= new Files();
        files.setUsersId(users.getId());
        files.setCreateTime(new Date());
        files.setName("默认文件夹");
        filesMapper.insertSelective(files);
        System.out.println("hahah");
        return new ResultBean<>();
    }
    @PostMapping(value = "/users/findPassword")
    public ResultBean<String> findPassword(@RequestParam(value = "email") String number,
                                         @RequestParam(value = "security") String securityCode,String password) {
        ValidataUtils.isNotNullByString(securityCode,"安全码不能为空");
        ValidataUtils.isNotNullByString(number,"安全码不能为空");
        ValidataUtils.isNotNullByString(password,"新密码不能为空");
        Users users = new Users();
        users.setEmail(number);
        Users users1 = userInfoMapper.selectOne(users);
        System.out.println(users1);
        ValidataUtils.isNotNull(users1,"邮箱不存在");
        if (!users1.getSecurity().equals(securityCode)){
            throw new CheckException("安全码不正确");
        }
        users.setId(users1.getId());
        users.setPassword(password);
        int i = userInfoMapper.updateByPrimaryKeySelective(users);
        return new ResultBean<>();
    }





}