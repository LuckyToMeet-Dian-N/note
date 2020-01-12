package com.gentle.controller.user;

import com.gentle.bean.vo.FileType;
import com.gentle.exception.CheckException;
import com.gentle.result.ResultBean;
import com.gentle.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/18 : 15:56
 */
@RestController
@RequestMapping(value = "/users/")
public class UserFileController {
//
//    @Autowired
//    FileService fileService;
//
//    @PostMapping(value = "createFile")
//    public ResultBean<String> createFile(@RequestParam(value = "folderName") String directoryName,
//                                         @RequestParam(value = "currLocal",defaultValue = "") String currPath) {
//        if (StringUtils.isEmpty(directoryName)){
//            throw new CheckException("路径不可为空");
//        }
//        return new ResultBean<>(fileService.createFile(directoryName,currPath));
//    }
//
//    @PostMapping(value = "updateFile")
//    public ResultBean<String> updateFile(String directoryName,String currPath,String oldFolderName) {
//        if (StringUtils.isEmpty(directoryName)||StringUtils.isEmpty(currPath)||StringUtils.isEmpty(oldFolderName)){
//            throw new CheckException("路径不可为空");
//        }
//
//        return new ResultBean<>(fileService.updateFileName(directoryName,currPath,oldFolderName));
//    }
//    @PostMapping(value = "deleteFile")
//    public ResultBean<String> deleteFile(@RequestParam(value = "folderName")String directoryName,
//                                         @RequestParam(value = "currLocal",defaultValue = "") String currPath) {
//        if (StringUtils.isEmpty(directoryName)||StringUtils.isEmpty(currPath)){
//            throw new CheckException("路径不可为空");
//        }
//
//        return new ResultBean<>(fileService.deleteFile(directoryName,currPath));
//    }
//    @GetMapping(value = "listFile")
//    public ResultBean<List<FileType>> listFile(@RequestParam(value = "currLocal",defaultValue = "") String path) {
//        if (StringUtils.isEmpty(path)){
//            throw new CheckException("路径不可为空");
//        }
//        return new ResultBean<>(fileService.listFileInfo(path));
//    }


}
