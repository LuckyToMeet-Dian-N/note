package com.gentle.controller.admin;

import com.gentle.bean.po.Versions;
import com.gentle.mapper.VersionsMapper;
import com.gentle.result.ResultBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 23:33
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class VersionsController {

    @Resource
    VersionsMapper versionsMapper;

    @PostMapping(value = "deleteVersions")
    public ResultBean<String> delete(Integer id) {
        versionsMapper.deleteByPrimaryKey(id);
        return new ResultBean<>();
    }
    @GetMapping("selectAllVersions")
    public ResultBean<List<Versions>> select() {
        return new ResultBean<>(versionsMapper.selectAll());
    }
}
