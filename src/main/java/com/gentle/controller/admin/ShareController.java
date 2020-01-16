package com.gentle.controller.admin;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Share;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.ShareVO;
import com.gentle.mapper.NoteMapper;
import com.gentle.mapper.ShareMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 22:46
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class ShareController {

    @Resource
    ShareMapper shareMapper;
    @Resource
    NoteMapper noteMapper;

    @Resource
    UserInfoMapper userInfoMapper;
    @PostMapping(value = "deleteShare")
    public ResultBean<String> delete( Integer id) {
        shareMapper.deleteByPrimaryKey(id);
        return new ResultBean<>();
    }

    @GetMapping(value = "getAllShare")
    public ResultBean<List<ShareVO>> select(@RequestParam(value = "noteType",required = false)Integer noteType) {
        List<Share> shareVoList;
        shareVoList = shareMapper.selectAll();
        List<ShareVO> shareVOS = new ArrayList<>();
        shareVoList.forEach(share -> {
            ShareVO shareVO = new ShareVO();
            if (StringUtils.isEmpty(noteType)) {
                Note note = noteMapper.selectByPrimaryKey(share.getNoteId());
                getSelect(shareVOS, share, shareVO, note);
            } else {
                Note note = noteMapper.selectByPrimaryKey(share.getNoteId());
                if (note.getNoteType().equals(noteType)){
                    getSelect(shareVOS, share, shareVO, note);
                }
            }
        });
        return new ResultBean<>(shareVOS);
    }

    private void getSelect(List<ShareVO> shareVOS, Share share, ShareVO shareVO, Note note) {
        BeanUtils.copyProperties(note, shareVO);
        shareVO.setNoteId(note.getId());
        Users users = userInfoMapper.selectByPrimaryKey(share.getUserId());
        shareVO.setUserName(users.getUserName());
        shareVO.setUserId(users.getId());
        shareVOS.add(shareVO);
    }


}
