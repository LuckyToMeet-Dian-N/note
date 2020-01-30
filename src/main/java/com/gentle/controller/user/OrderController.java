package com.gentle.controller.user;

import com.gentle.bean.po.Files;
import com.gentle.bean.po.Note;
import com.gentle.bean.po.Orders;
import com.gentle.bean.po.Users;
import com.gentle.exception.CheckException;
import com.gentle.mapper.OrdersMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import com.gentle.utils.RequestAndResponseUtils;
import com.gentle.utils.UuidUtil;
import com.gentle.utils.ValidataUtils;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "getOrderList")
    public ResultBean<List<Orders>> getOrderList() {
        Orders orders = new Orders();
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        orders.setUserId(users.getId());
        List<Orders> select = ordersMapper.select(orders);
        return new ResultBean<>(select);
    }

    @Resource
    UserInfoMapper userInfoMapper;

    @PostMapping(value = "updateOrderStatus")
    public ResultBean<String> updateOrderStatus(String orderNumber) {
        Orders orders = new Orders();
        orders.setOrderNumber(orderNumber);
        Orders orders1 = ordersMapper.selectOne(orders);
        if (orders1==null){
            throw new CheckException("订单号不存在");
        }
        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
        Users users1 = userInfoMapper.selectByPrimaryKey(users.getId());
        if (!(users1.getBalances()-10>=0)){
            throw new CheckException("余额不足，请先充值");
        }
        Users users2 = new Users();
        users2.setId(users.getId());
        users2.setUserType(1);
        userInfoMapper.updateByPrimaryKeySelective(users2);

        orders.setId(orders1.getId());
        orders.setPayState(10);
        orders.setPayState(1);
        ordersMapper.updateByPrimaryKeySelective(orders);
        return new ResultBean<>("支付成功");
    }

//    @PostMapping(value = "moneyBack")
//    public ResultBean<String> moneyBack(String orderNumber) {
//        Orders orders = new Orders();
//        orders.setOrderNumber(orderNumber);
//        Orders orders1 = ordersMapper.selectOne(orders);
//        if (orders1==null){
//            throw new CheckException("订单号不存在");
//        }
//        Users users  = (Users) RequestAndResponseUtils.getRequest().getAttribute("users");
//
//        Users users1 = userInfoMapper.selectByPrimaryKey(users.getId());
//        Users users2 = new Users();
//        users.setId(users.getId());
//        users.setBalances(users1.getBalances()+10);
//        userInfoMapper.updateByPrimaryKeySelective(users2);
//        orders.setId(orders1.getId());
//        orders.setPayState(3);
//        ordersMapper.updateByPrimaryKeySelective(orders);
//        return new ResultBean<>("支付成功");
//    }
}
