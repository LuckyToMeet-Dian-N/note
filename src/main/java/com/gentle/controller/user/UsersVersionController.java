package com.gentle.controller.user;

import com.gentle.bean.po.Users;
import com.gentle.bean.po.Versions;
import com.gentle.mapper.VersionsMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/30 : 14:36
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")
public class UsersVersionController {

    @Resource
    VersionsMapper versionsMapper;

    @GetMapping(value = "getVersion")
    public ResultBean<Versions> getUserInfo() {
        List<Versions> versions = versionsMapper.selectAll();
        Versions versions1 = versions.get(versions.size() - 1);
        return new ResultBean<>(versions1);
    }


}
