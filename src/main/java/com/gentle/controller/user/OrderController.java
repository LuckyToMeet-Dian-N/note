package com.gentle.controller.user;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Orders;
import com.gentle.bean.po.Users;
import com.gentle.mapper.OrdersMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.UuidUtil;
import com.gentle.utils.ValidataUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Gentle
 * @date 2020/01/30 : 00:17
 */
@RestController
@RequestMapping(value = "/api/users/")
@CrossOrigin("*")
public class OrderController {
    @Resource
    OrdersMapper ordersMapper;

    @PostMapping(value = "createOrders")
    public ResultBean<String> createOrders() {
        Orders orders = new Orders();
        orders.setCreateTime(new Date());
        String uuid = UuidUtil.get32UUID();
        orders.setOrderNumber(uuid);
        orders.setPayState(0);
        int i = ordersMapper.insertSelective(orders);
        return new ResultBean<>("创建订单成功");
    }


}
