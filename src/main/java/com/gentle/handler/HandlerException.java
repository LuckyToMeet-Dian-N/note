package com.gentle.handler;

import com.gentle.exception.CheckException;
import com.gentle.exception.UnloginException;
import com.gentle.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SignatureException;

/**
 * 异常处理器
 * @author Gentle
 */
@ControllerAdvice
@Slf4j
public class HandlerException {
    /**
     * 处理未知异常
     *
     * @param e
     * @return
     */

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean unknowException(Exception e) {
        log.error("系统出现未知异常", e);
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(ResultBean.UNKNOWN_EXCEPTION);
        resultBean.setMsg("系统出现未知异常，请于管理员联系");
        /**
         * 未知异常的话，这里写逻辑，发邮件，发短信都可以、、
         */
        return resultBean;
    }

    /**
     * 处理校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = CheckException.class)
    @ResponseBody
    public ResultBean handlerCheckException(CheckException e) {
        log.error("发生了已知错误："+e.getMessage());
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(ResultBean.CHECK_FAIL);
        resultBean.setMsg(e.getMessage());
        return resultBean;
    }



    /**
     * 处理身份被串改异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = SignatureException.class)
    @ResponseBody
    public ResultBean expiredJwtException(SignatureException e) {
        log.error("身份不正确："+ e.getMessage());
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(ResultBean.NO_PERMISSION);
        resultBean.setMsg("您的凭证有误");
        return resultBean;
    }
    /**
     * 处理未登录异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = UnloginException.class)
    @ResponseBody
    public ResultBean unloginException(UnloginException e) {
        log.error("没有登录："+ e.getMessage());
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(ResultBean.NO_LOGIN);
        resultBean.setMsg(e.getMessage());
        return resultBean;
    }
}