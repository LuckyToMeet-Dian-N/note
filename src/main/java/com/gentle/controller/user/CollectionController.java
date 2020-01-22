package com.gentle.controller.user;

import com.gentle.bean.po.Collections;
import com.gentle.bean.po.Users;
import com.gentle.mapper.CollectionMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 15:08
 * 收藏类接口
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")

public class CollectionController {
    @Resource
    CollectionMapper collectionMapper;

    @PostMapping("insertCollection")
    public ResultBean<String> insert(Integer noteId) {
      Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Collections collections = new Collections();
        collections.setCreateTime(new Date());
        collections.setNoteId(noteId);
        collections.setUpdateTime(new Date());
        collections.setUsersId(users.getId());
        collectionMapper.insert(collections);
        return new ResultBean<>();
    }

    @PostMapping("deleteCollection")
    public ResultBean<String> delete(Integer id) {
        collectionMapper.deleteByPrimaryKey(id);
        return new ResultBean<>();
    }

    @GetMapping("getAllCollections")
    public ResultBean< List<Collections>> select() {
        List<Collections> collections = collectionMapper.selectAll();
        return new ResultBean<>(collections);
    }
}
