package com.gentle.aop;

import com.gentle.exception.UnloginException;
import com.gentle.utils.RequestAndResponseUtils;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String,Object > map = new HashMap<>();

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

        if (map.get(uuId) == null){
            throw  new UnloginException("非法登录");
        }

        RequestAndResponseUtils.getRequest().setAttribute("users",map.get(uuId));
    }
}