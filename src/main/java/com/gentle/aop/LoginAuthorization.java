package com.gentle.aop;

import com.gentle.bean.po.Users;
import com.gentle.exception.UnloginException;
import com.gentle.utils.JsonUtil;
import com.gentle.utils.RedisService;
import com.gentle.utils.RequestAndResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description: 拦截未登录的用户
 * @Author: Gentle
 * @date 2018/8/18 23:53
 */
//@Order(5)
//@Slf4j
//@Component
//@Aspect
public class LoginAuthorization {
    @Autowired
    RedisService redisService;

    @Pointcut("execution(public * com.gentle.controller.user.*.*(..))")
    public void authorization() {
    }

    @Before("authorization()")
    public void handlerControllerMethod() throws Exception {

        HttpServletRequest httpServletRequest = RequestAndResponseUtils.getRequest();
        String uuId = httpServletRequest.getHeader("token");
        if (uuId == null || uuId.isEmpty()) {
            throw  new UnloginException("没有登录");
        }

        String s = redisService.hashGet(uuId,"users");
        Users studentInfo = JsonUtil.jsonToObject(s, Users.class);
        if (studentInfo == null){
            throw  new UnloginException("非法登录");
        }

        RequestAndResponseUtils.getRequest().setAttribute("users",studentInfo);
    }
}