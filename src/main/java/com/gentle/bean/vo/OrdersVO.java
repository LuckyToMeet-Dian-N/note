package com.gentle.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Gentle
 * @date 2020/01/11 : 20:43
 */
@Data
public class OrdersVO {

    private Integer id;
    private String orderNumber;
    private Integer userId;
    private Integer payMoney;
    private Integer payState;
    private Date createTime;
    private String userName;
}
