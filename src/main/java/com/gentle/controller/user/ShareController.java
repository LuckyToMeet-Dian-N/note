package com.gentle.controller.user;

import com.gentle.bean.po.Note;
import com.gentle.bean.po.Share;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.ShareVO;
import com.gentle.bean.vo.UserShareVO;
import com.gentle.exception.CheckException;
import com.gentle.mapper.NoteMapper;
import com.gentle.mapper.ShareMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/19 : 15:05
 * 这里写分享接口
 */
@RequestMapping(value = "/api/users/")
@RestController
@CrossOrigin("*")
public class ShareController {

    @Resource
    ShareMapper shareMapper ;
    @Resource
    NoteMapper noteMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    @PostMapping("insertShare")
    public ResultBean<String> insert(Integer noteId) {
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Share share1 = new Share();
        share1.setNoteId(noteId);
        List<Share> select = shareMapper.select(share1);
        if (!select.isEmpty()){
            throw new CheckException("已经分享，无法继续分享");
        }
        Share share = new Share();
        share.setUserId(users.getId());
        share.setCreateTime(new Date());
        share.setNoteId(noteId);
        share.setNumber(0);
        share.setUpdateTime(new Date());
        int insert = shareMapper.insert(share);
        return new ResultBean<>();
    }

    @PostMapping("deleteShare")
    public ResultBean<String> delete(Integer id) {
        shareMapper.deleteByPrimaryKey(id);
        return new ResultBean<>();
    }

    /**
     * 分享列表
     * @return
     */
    @GetMapping("selectShareInfo")
    public ResultBean<UserShareVO> selectShareList( ) {
        UserShareVO userShareVO = new UserShareVO();
        List<Share> shares = shareMapper.selectAll();
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        List<ShareVO> allShares = getShareVoS(shares);
        userShareVO.setAllShare(allShares);
        Share share = new Share();
        share.setUserId(users.getId());
        List<Share> select = shareMapper.select(share);
        List<ShareVO> myShares = getShareVoS(select);
        userShareVO.setMyShare(myShares);
        return new ResultBean<>(userShareVO);
    }

    private List<ShareVO> getShareVoS(List<Share> shares) {
        List<ShareVO> shareVOS = new ArrayList<>();
        shares.forEach(share -> {
            ShareVO shareVO = new ShareVO();
            Note note = noteMapper.selectByPrimaryKey(share.getNoteId());
            getSelect(shareVOS, share, shareVO, note);
        });
        return shareVOS;
    }

    private void getSelect(List<ShareVO> shareVOS, Share share, ShareVO shareVO, Note note) {

        BeanUtils.copyProperties(note, shareVO);
        shareVO.setId(share.getId());
        shareVO.setNoteId(note.getId());
        Users users = userInfoMapper.selectByPrimaryKey(share.getUserId());
        shareVO.setUserName(users.getUserName());
        shareVO.setUserId(users.getId());
        shareVOS.add(shareVO);
    }
}
