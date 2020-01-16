package com.gentle.controller.admin;

import com.gentle.bean.po.Note;
import com.gentle.bean.po.Share;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.ConsoleVO;
import com.gentle.bean.vo.ShareVO;
import com.gentle.mapper.NoteMapper;
import com.gentle.mapper.ShareMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/16 : 23:17
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class WorkConsoleController {

    @Resource
    NoteMapper noteMapper;

    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    ShareMapper shareMapper;

    @GetMapping(value = "allInfo")
    public ResultBean<ConsoleVO> select() {
        ConsoleVO consoleVO = new ConsoleVO();
        int i = noteMapper.selectCount(new Note());
        consoleVO.setAllNoteNum(i);
        int i1 = userInfoMapper.selectCount(new Users());
        consoleVO.setAllUserNum(i1);
        int i2 = shareMapper.selectCount(new Share());
        consoleVO.setAllShareNum(i2);

        consoleVO.setShareNumber(shareMapper.selectTimebyMon());
        consoleVO.setUserNumber(userInfoMapper.selectTimebyMon());
        List<Share> shares = shareMapper.selectAll();
        List<ShareVO> shareVOS = new ArrayList<>();
        shares.forEach(share -> {
            ShareVO shareVO = new ShareVO();
                Note note = noteMapper.selectByPrimaryKey(share.getNoteId());
                getSelect(shareVOS, share, shareVO, note);
        });
        consoleVO.setNodeVos(shareVOS);

        return new ResultBean<>(consoleVO);
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
