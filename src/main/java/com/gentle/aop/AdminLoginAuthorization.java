package com.gentle.aop;

import com.gentle.bean.po.Admins;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Gentle
 * @date 2018/12/08 16:35
 */
@Order(5)
@Slf4j
@Component
@Aspect
public class AdminLoginAuthorization {

    public static Map<String,Object > map = new HashMap<>();

    @Pointcut("execution(public * com.gentle.controller.admin.*.*(..))")
    public void authorization1() {
    }
    @Before("authorization1()")
    public void handlerControllerMethod1() throws Exception {
        HttpServletRequest httpServletRequest = RequestAndResponseUtils.getRequest();
        String uuId = httpServletRequest.getHeader("token");
        if (uuId == null || uuId.isEmpty()) {
            throw new UnloginException("没有登录");
        }
        if(map.get(uuId)==null){
            throw new UnloginException("没有登录");
        }

        RequestAndResponseUtils.getRequest().setAttribute("admin",map.get(uuId));

    }
}