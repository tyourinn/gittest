package com.example.fruits.interceptor;

import com.example.fruits.domain.Users;
import com.example.fruits.utils.UserContextUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

public class UserLoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("UserLoginInterceptor");

        // 判断session
        HttpSession session  = request.getSession();
        // 从session中取出用户身份信息
        Users userInfo = UserContextUtil.getUser();
        // session存在时，放行
        if (userInfo!=null) {
            return true;
        }

        String uri = request.getRequestURI();
        StringBuffer params = new StringBuffer();
        if(!uri.equals("/js/axios.min.map") && !uri.equals("/favicon.ico")){
            Map<String, String[]> parameterMap = request.getParameterMap();
            if(!CollectionUtils.isEmpty(parameterMap)){
                Set<String> strings = parameterMap.keySet();
                for (String key : strings) {
                    params.append("&").append(key).append("=").append(parameterMap.get(key)[0]);
                }
                params.setCharAt(0,'?');
                uri += params;
            }
            session.setAttribute("toHtmlURI",uri);
        }
        // 执行这里表示用户身份需要认证，跳转登陆页面
        response.sendRedirect("/login");
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}