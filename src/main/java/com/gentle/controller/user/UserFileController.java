package com.gentle.controller.user;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.FilesVO;
import com.gentle.exception.CheckException;
import com.gentle.mapper.FilesMapper;
import com.gentle.mapper.NoteMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");

        Files  files = new Files();
        files.setUsersId(users.getId());
        files.setCreateTime(new Date());
        files.setName(directoryName);
        filesMapper.insertSelective(files);
        return new ResultBean<>();
    }

    @PostMapping(value = "updateFile")
    public ResultBean<String> updateFile(Files files) {
        if (StringUtils.isEmpty(files.getName())){
            throw new CheckException("文件名不可为空");
        }
        ValidataUtils.isNotNull(files.getId(),"id不能为空");
        filesMapper.updateByPrimaryKeySelective(files);
        return new ResultBean<>();
    }
    @PostMapping(value = "deleteFile")
    public ResultBean<String> deleteFile(Integer id) {
        ValidataUtils.isNotNull(id,"id 不能为空");
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Files files = new Files();
        files.setUsersId(users.getId());
        List<Files> select = filesMapper.select(files);
        if (select.get(0).getId().equals(id)){
            throw new CheckException("默认文件夹无法删除");
        }
        Files files1 = filesMapper.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(files1.getNoteList())){
            String[] split = files1.getNoteList().split(",");
            List<String> list = Arrays.asList(split);
            Set<String> set = new HashSet<>(list);
            if (!StringUtils.isEmpty(select.get(0).getNoteList())){
                String[] split1 = select.get(0).getNoteList().split(",");
                List<String> list1 = Arrays.asList(split1);
                set.addAll(list1);
            }
            String join = org.apache.commons.lang3.StringUtils.join(new ArrayList<>(set), ",");
            Files files2 = new Files();
            files2.setId(select.get(0).getId());
            files2.setNoteList(join);
            filesMapper.updateByPrimaryKeySelective(files2);
        }
         filesMapper.deleteByPrimaryKey(id);
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
            AtomicBoolean temp= new AtomicBoolean();
            temp.set(false);
            List<Integer> noExistNote = new ArrayList<>();
            FilesVO filesVO = new FilesVO();
            BeanUtils.copyProperties(files1,filesVO);
            List<Note>  list = new ArrayList<>();
            if (!StringUtils.isEmpty(files1.getNoteList())){
                String[] split = files1.getNoteList().split(",");
                Arrays.stream(split).forEach(e->{
                    Note note = mapper.selectByPrimaryKey(e);
                    if (note!=null){
                        list.add(note);
                        noExistNote.add(note.getId());
                    }else {
                        temp.set(true);
                    }
                });
                if (temp.get()){
                    Files  files2 = new Files();
                    files2.setId(files1.getId());
                    files2.setNoteList(org.apache.commons.lang3.StringUtils.join(noExistNote, ","));
                    filesMapper.updateByPrimaryKeySelective(files2);
                }
            }

            filesVO.setNoteList(list);
            filesVoArrayList.add(filesVO);
        });



        return new ResultBean<>(filesVoArrayList);
    }
    @Resource
    UserInfoMapper userInfoMapper;
    @PostMapping(value = "usersUploadFile")
    @Transactional
    public ResultBean<String> uploadOralLanguage(@RequestParam("file") MultipartFile file,Note note){
        ValidataUtils.isNotNull(file,"文件不存在，请检查上传文件");
        ValidataUtils.isNotNullByString(note.getNoteType(),"文稿类型不能为空！");
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        Users users1 = userInfoMapper.selectByPrimaryKey(users.getId());
        if (note.getNoteType()==1){
            if (!(substring.equals(".jpg") || substring.equals(".png"))){
                throw new CheckException("文件类型不支持");
            }
        }else if (note.getNoteType()==2){
            if (!(substring.equals(".mp4"))){
                throw new CheckException("文件类型不支持");
            }
            if (users1.getUserType()==0){
                throw new CheckException("非会员用户不支持使用视频文稿！");
            }else {
                if (file.getSize()>1024*1024*10){
                    throw new CheckException("会员用户最大可上传 10M 文件");
                }
            }
        }else {
            throw new CheckException("不支持的类型");
        }
        String s = FileUpload.fileUp(file, FileConstants.DEFAULT_ROOT_PATH, UuidUtil.get32UUID());
        String path = "http://192.168.37.1:8080/"+s;
        note.setUsersId(users.getId());
        note.setNoteTitle(file.getOriginalFilename());
        note.setCreateTime(new Date());
        note.setUpdateTime(new Date());
        note.setFilePath(path);
        int i = mapper.insertSelective(note);
        Files selectFile= new Files();
        selectFile.setUsersId(users.getId());
        List<Files> select = filesMapper.select(selectFile);
        String noteList = select.get(0).getNoteList();
        Files files = new Files();
        files.setId(select.get(0).getId());
        files.setNoteList(noteList+","+note.getId());
        filesMapper.updateByPrimaryKeySelective(files);
        return new ResultBean<>(path);
    }


}
