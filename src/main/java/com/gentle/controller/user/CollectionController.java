package com.gentle.controller.user;

import com.gentle.bean.po.Collections;
import com.gentle.bean.po.Users;
import com.gentle.mapper.CollectionMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 15:08
 * 收藏类接口
 */
@RestController
@RequestMapping(value = "/users/")
public class CollectionController {
    @Resource
    CollectionMapper collectionMapper;

    @PostMapping()
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
//
    @PostMapping()
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
