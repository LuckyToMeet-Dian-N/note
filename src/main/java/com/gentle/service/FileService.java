package com.gentle.service;

import com.gentle.bean.vo.FileType;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/09/18 : 16:22
 */
public interface FileService {

     List<FileType> listFileInfo(String path);

     String createFile(String directoryName,String currPath);

     String deleteFile(String directoryName,String currPath);

     String updateFileName(String directoryName,String currPath,String oldFolderName);
}
