package com.gentle.controller.admin;

import com.gentle.bean.po.Versions;
import com.gentle.mapper.VersionsMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.FileConstants;
import com.gentle.utils.FileUpload;
import com.gentle.utils.UuidUtil;
import com.gentle.utils.ValidataUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
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


    @PostMapping(value = "deployVersions")
    @Transactional
    public ResultBean<String> uploadOralLanguage(@RequestParam("file") MultipartFile file, Versions versions){
        System.out.println(versions);
        ValidataUtils.isNotNullByString(versions.getDescription(),"版本描述不能为空！");
        ValidataUtils.isNotNullByString(versions.getVersion(),"版本号不能为空！");
        String s = FileUpload.fileUp(file, FileConstants.DEFAULT_ROOT_PATH, UuidUtil.get32UUID());
        String  path = "http://localhost:8080/"+s;
        versions.setPath(path);
        versions.setCreateTime(new Date());
        int insert = versionsMapper.insert(versions);
        return new ResultBean<>("上传成功");
    }


}
