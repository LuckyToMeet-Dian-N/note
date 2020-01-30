package com.gentle.aop;

import com.gentle.bean.po.Users;
import com.gentle.exception.UnloginException;
import com.gentle.utils.RequestAndResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 拦截未登录的用户
 * @Author: Gentle
 * @date 2018/8/18 23:53
 */
@Order(4)
@Slf4j
@Component
@Aspect
public class LoginAuthorization {

    public static Map<String,Object > map = new HashMap<>();

    @Pointcut("execution(public * com.gentle.controller.user.*.*(..))")
    public void authorization() {
    }

    @Before("authorization()")
    public void handlerControllerMethod() throws Exception {
        if (map.isEmpty()){
            Users users = new Users();
            users.setId(1);
            users.setPassword("123456");
            users.setBalances(0);
            users.setUpdateTime(new Date());
            users.setCreateTime(new Date());
            users.setRegion("哈哈");
            users.setAge(10);
            users.setEmail("hahah@qq.com");
            users.setUserType(0);
            users.setUserName("Gentle");
            map.put("1",users);
        }
//        HttpServletRequest httpServletRequest = RequestAndResponseUtils.getRequest();
//        String uuId = httpServletRequest.getHeader("token");
//        if (uuId == null || uuId.isEmpty()) {
//            throw  new UnloginException("没有登录");
//        }
//
//        if (map.get(uuId) == null){
//            throw  new UnloginException("非法登录");
//        }

//        RequestAndResponseUtils.getRequest().setAttribute("users",map.get(uuId));
        RequestAndResponseUtils.getRequest().setAttribute("users",map.get("1"));
    }
}