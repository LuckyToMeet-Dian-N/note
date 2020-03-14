package com.gentle.controller.user;

import com.gentle.bean.po.Collections;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.CollectionsVO;
import com.gentle.exception.CheckException;
import com.gentle.mapper.CollectionMapper;
import com.gentle.mapper.NoteMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        Collections collections1 = new Collections();
        collections1.setUsersId(users.getId());
        collections1.setNoteId(noteId);
        Collections collections2 = collectionMapper.selectOne(collections1);
        if (collections2!=null){
            throw new CheckException("已经收藏过，无法再次收藏");
        }
        Collections collections3 = new Collections();
        collections3.setUsersId(users.getId());
        int count = collectionMapper.selectCount(collections3);
        if (count+1>20){
            if (users.getUserType()==0){
                throw new CheckException("非会员用户最多收藏 20 个文稿");
            }else {
                if (count+1>50){
                    throw new CheckException("会员用户最多收藏 50 个文稿");
                }
            }
        }
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
    @Resource
    NoteMapper noteMapper;
    @Resource
    UserInfoMapper userInfoMapper;

    @GetMapping("getAllCollections")
    public ResultBean< List<CollectionsVO>> select() {
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Collections collections = new Collections();
        collections.setUsersId(users.getId());
        List<Collections> collections1 = collectionMapper.select(collections);
        List<CollectionsVO> list = new ArrayList<>();
        collections1.forEach(e->{
            CollectionsVO collectionsVO = new CollectionsVO();
            collectionsVO.setId(e.getId());
            Note note = noteMapper.selectByPrimaryKey(e.getNoteId());
            collectionsVO.setNote(note);
            collectionsVO.setCreateTime(e.getCreateTime());
            collectionsVO.setUsersId(e.getUsersId());
            Users users1 = userInfoMapper.selectByPrimaryKey(e.getUsersId());
            collectionsVO.setUserName(users1.getUserName());
            list.add(collectionsVO);
        });
        return new ResultBean<>(list);
    }
}
