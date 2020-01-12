package com.gentle.bean.po;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Gentle
 * @date 2020/01/11 : 20:24
 */
@Data
public class Orders {
    @Id
    private Integer id;
    private String orderNumber;
    private Integer userId;
    private Integer payMoney;
    private Integer payState;
    private Date createTime;

}
