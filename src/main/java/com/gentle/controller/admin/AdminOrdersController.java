package com.gentle.controller.admin;

import com.gentle.bean.po.Orders;
import com.gentle.bean.po.Users;
import com.gentle.bean.vo.OrdersVO;
import com.gentle.mapper.OrdersMapper;
import com.gentle.mapper.UserInfoMapper;
import com.gentle.result.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/01/11 : 20:38
 */
@RestController
@RequestMapping(value = "/api/admin/")
@CrossOrigin("*")
public class AdminOrdersController {


    @Resource
    private OrdersMapper ordersMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @GetMapping("listOrders")
    public ResultBean<List<OrdersVO>> select(@RequestParam(value = "userId",required = false)Integer userId,
                                             @RequestParam(value = "payState") Integer payState) {
        List<Orders> orders;
        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(payState)) {
            orders = ordersMapper.selectAll();
        }else{
            Orders orders1 = new Orders();
            if (!StringUtils.isEmpty(userId)){
                orders1.setUserId(userId);
            }
            if (!StringUtils.isEmpty(payState)){
                orders1.setPayState(payState);
            }
            orders= ordersMapper.select(orders1);
        }
        List<OrdersVO> list = new ArrayList<>();
            orders.forEach(e -> {
                OrdersVO ordersVO;
                Users users = userInfoMapper.selectByPrimaryKey(e.getId());
                ordersVO = new OrdersVO();
                BeanUtils.copyProperties(e, ordersVO);
                ordersVO.setUserName(users.getUserName());
                list.add(ordersVO);
            });

        return new ResultBean<>(list);
    }

    @PostMapping(value = "deleteOrders")
    public ResultBean<Integer> deleteUsers(Integer ordersId) {
        return new ResultBean<>(ordersMapper.deleteByPrimaryKey(ordersId));
    }

    @PostMapping("updateOrders")
    public ResultBean<Integer> update(OrdersVO ordersVO) {
        System.out.println(ordersVO);
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersVO,orders);
        return new ResultBean<>(ordersMapper.updateByPrimaryKeySelective(orders));
    }


}
