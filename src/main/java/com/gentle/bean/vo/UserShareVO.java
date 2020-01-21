package com.gentle.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/21 : 17:06
 */
@Data
public class UserShareVO {

    private List<ShareVO> allShare;

    private List<ShareVO> myShare;


}
