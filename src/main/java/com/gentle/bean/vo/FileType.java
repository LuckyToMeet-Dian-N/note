package com.gentle.bean.vo;

import lombok.Data;

/**
 * @author Gentle
 * @date 2019/09/18 : 16:51
 */
@Data
public class FileType {

    private String fileName;
    /**
     * 0-文件，1-文件夹
     */
    private Integer fileType;


}
