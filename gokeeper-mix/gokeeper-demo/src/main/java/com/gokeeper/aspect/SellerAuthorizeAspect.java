package com.gokeeper.aspect;

import com.gokeeper.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * AOP切面编程验证登录
 * @author Created by Akk_Mac
 * Date: 2017/8/30 上午11:05
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {


    //拦截除了登录登出之外的操作,这是设置拦截范围
    @Pointcut("execution(* com.gokeeper.controller.api.*.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //1. 查询session
        HttpSession session = request.getSession(false);
        if(session == null) {
            //log.warn("【登录校验】" + request.getRequestURI() + "的session为空");
            throw new SellerAuthorizeException();
        }

    }

}
