package com.example.fruits.controller;

import com.example.fruits.domain.Users;
import com.example.fruits.service.UsersService;
import com.example.fruits.utils.UserContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    //注册
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> register(Users users){
        usersService.register(users);
        return ResponseEntity.ok().build();
    }

    //登录
    @PostMapping("/login")
    public ResponseEntity<String> login(Users users,HttpSession session){
        usersService.login(users);
        //判断是否要跳转到用户之前要访问的html页面中
        String toHtmlURI = (String) session.getAttribute("toHtmlURI");
        if(!StringUtils.isBlank(toHtmlURI)){
            session.removeAttribute("toHtmlURI");
        }else{
            toHtmlURI = "/index";
        }
      return ResponseEntity.ok(toHtmlURI);
    }

    //退出
    @GetMapping("/exit")
    public void exit(HttpServletResponse response){
        UserContextUtil.getsession().invalidate();
        try {
            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户是否登录验证
     * @param toUrl 用户未登录，前端页面希望用户登录后跳转到的请求
     * @param session
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<Boolean> isLong(@RequestParam(value = "url",required = false) String toUrl,
                                          HttpSession session){
        Users user = UserContextUtil.getUser();
        if(!StringUtils.isBlank(toUrl)){
            session.setAttribute("toHtmlURI",toUrl);
        }
        return ResponseEntity.ok(user==null?false:true);
    }


}
