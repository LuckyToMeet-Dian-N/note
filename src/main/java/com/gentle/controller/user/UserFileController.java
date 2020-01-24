package com.gentle.controller.user;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.FileType;
import com.gentle.bean.vo.FilesVO;
import com.gentle.exception.CheckException;
import com.gentle.mapper.FilesMapper;
import com.gentle.mapper.NoteMapper;
import com.gentle.result.ResultBean;
import com.gentle.service.FileService;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.ValidataUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/18 : 15:56
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")
public class UserFileController {
    @Resource
    FilesMapper filesMapper;
    @Resource
    NoteMapper mapper;

    @PostMapping(value = "createFile")
    public ResultBean<String> createFile(@RequestParam(value = "folderName") String directoryName) {
        if (StringUtils.isEmpty(directoryName)){
            throw new CheckException("路径不可为空");
        }
        Files  files = new Files();
        files.setCreateTime(new Date());
        files.setName(directoryName);
        filesMapper.insertSelective(files);
        return new ResultBean<>();
    }

    @PostMapping(value = "updateFile")
    public ResultBean<String> updateFile(Files files) {
        if (StringUtils.isEmpty(files.getName())){
            throw new CheckException("路径不可为空");
        }
        ValidataUtils.isNotNull(files.getId(),"id不能为空");

        return new ResultBean<>();
    }
    @PostMapping(value = "deleteFile")
    public ResultBean<String> deleteFile(Integer id) {

        return new ResultBean<>();
    }
    @GetMapping(value = "listFile")
    public ResultBean<List<FilesVO>> listFile() {
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Files files = new Files();
        files.setUsersId(users.getId());
        List<Files> select = filesMapper.select(files);
        List<FilesVO> filesVoArrayList = new ArrayList<>();
        select.forEach(files1 -> {
            FilesVO filesVO = new FilesVO();
            BeanUtils.copyProperties(files1,filesVO);
            List<Note>  list = new ArrayList<>();
            if (!StringUtils.isEmpty(files1.getNoteList())){
                String[] split = files1.getNoteList().split(",");
                Arrays.stream(split).forEach(e->{
                    Note note = mapper.selectByPrimaryKey(e);
                    list.add(note);
                });
            }
            filesVO.setNoteList(list);
            filesVoArrayList.add(filesVO);
        });
        return new ResultBean<>(filesVoArrayList);
    }


}
