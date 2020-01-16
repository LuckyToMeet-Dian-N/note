package com.gentle.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/15 : 23:51
 */
@Data
public class ConsoleVO {

    private List<Integer> shareNumber;

    private List<ShareVO> nodeVos;

    private Integer allUserNum;

    private Integer allShareNum;

    private Integer allNoteNum;




}
