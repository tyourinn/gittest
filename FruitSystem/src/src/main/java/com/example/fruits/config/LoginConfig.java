package com.example.fruits.config;

import com.example.fruits.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * 
 * @Package: com.*.*.config 
 * @ClassName: LoginConfig 
 * @Description:拦截器配置
 * @author: zk
 * @date: 2019年9月19日 下午2:18:35
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
        registration.addPathPatterns("/**");                      //我的（个人中心）
        registration.excludePathPatterns("/login"); //登录页
        registration.excludePathPatterns("/login.html");
        registration.excludePathPatterns("/register");  //注册页
        registration.excludePathPatterns("/register.html");
        registration.excludePathPatterns("/index.html");    //首页
        registration.excludePathPatterns("/index");    //首页
        registration.excludePathPatterns("/fruits/**"); //获取水果详情
        registration.excludePathPatterns("/goods-details.html");
        registration.excludePathPatterns("/user/**");   //用户登录，注册，退出，
        registration.excludePathPatterns("/index.html","/","/user/login", "/**/*.css",
                "/**/*.js", "/**/*.png", "/**/*.jpg",
                "/**/*.jpeg", "/**/*.gif", "/**/fonts/*");
    }


}