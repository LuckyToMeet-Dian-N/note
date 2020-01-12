package com.gentle.service.impl;

import com.gentle.bean.po.Users;
import com.gentle.bean.vo.FileType;
import com.gentle.exception.CheckException;
import com.gentle.service.FileService;
import com.gentle.utils.RequestAndResponseUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Gentle
 * @date 2019/09/18 : 16:23
 */
@Service
public class FileServiceImpl implements FileService {
        private String DEFAULT_PATH="/home/";

    @Override
    public List<FileType> listFileInfo(String path) {

        File file = new File(getPath()+path);
        if (!file.exists()) {
            throw new CheckException("文件不存在");
        }
        File[] files = file.listFiles();
        List<FileType> list = new ArrayList<>();
        if (files == null || files.length <= 0) {
            return list;
        }
        FileType fileType;
        for (File f : files) {
            fileType = new FileType();
            fileType.setFileName(f.getName());
            if (f.isDirectory()) {
                fileType.setFileType(1);
            } else {
                fileType.setFileType(0);
            }
            list.add(fileType);
        }
        return list;
    }

    @Override
    public String createFile(String directoryName,String currPath) {

        if (currPath==null){
            currPath="";
        }
        File file = new File(getPath()+currPath+directoryName);
        if (file.exists()){
            throw new CheckException("文件夹已经存在");
        }
        boolean mkdirs = file.mkdirs();

        return "创建成功";
    }

    @Override
    public String deleteFile(String directoryName,String currPath) {

        File file = new File(getPath()+currPath+directoryName);

        if (!file.exists()){
            throw new CheckException("文件不存在，无法删除");
        }
        if (!file.delete()){
            throw new CheckException("该文件夹存存在文件无法删除");
        }

        return "删除成功";
    }

    @Override
    public String updateFileName(String directoryName, String currPath,String oldFolderName) {

        File file = new File(getPath()+currPath+oldFolderName);
        if (!file.exists()){
            throw new CheckException("文件不存在，无法修改");
        }
        boolean b = file.renameTo(new File(getPath()+currPath + directoryName));
        if (b){

            return "修改成功";
        }
        return "修改失败";
    }

    private String getPath(){
        Users users = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");

        return DEFAULT_PATH+users.getEmail()+"/";
    }


}
