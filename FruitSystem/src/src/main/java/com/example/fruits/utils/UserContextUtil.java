package com.example.fruits.utils;

import com.example.fruits.domain.Users;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Name UserContextUtil
 * @Descr 用户上下文对象：设置和获取HttpSession登录的用户
 * @author lne
 * @date 2016年10月16日下午1:04:21
 */
public class UserContextUtil {
    public static final String LOGIN_USER = "loginUser";

    public static void setUser(Users users, HttpSession session) {
        session.setAttribute(LOGIN_USER, users);
    }

    public static void setUser(Users users) {
        getsession().setAttribute(LOGIN_USER, users);
    }

    public static Users getUser(HttpSession session) {
        return (Users)session.getAttribute(LOGIN_USER);
    }

    public static Users getUser() {
        return (Users)getsession().getAttribute(LOGIN_USER);
    }

    public static HttpSession getsession() {
        return getRequest().getSession();
    }

    public static HttpServletRequest getRequest()
    {
      ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = requestAttributes.getRequest();
      return request;
    }
}