package com.tan.springcloud2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cookie")
public class CookieController {
    @RequestMapping("/set")
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        // 创建cookie对象
        Cookie compCookie = new Cookie("computer", "HP");
        // 服务器把cookie响应给客户端，所有的cookie对象，都会在服务器端创建，通过http响应给客户端(浏览器)


        Cookie keyCookie = new Cookie("key", "doubleflybird");

        Cookie mouseCookie = new Cookie("mouse", "leishe");
        //如果不设置使用时间，那么将取不到Cookie的值
        mouseCookie.setMaxAge(60*60*24*30);
        // 项目名(在tomcat中部署的项目名)
        System.out.println(request.getContextPath());
        // /zzsxt/shopping.jsp
        // 一旦设置了cookie的路径，就只能通过这一个路径才能获取到cookie信息
        keyCookie.setPath(request.getContextPath()+"/getCookie.sxt");

        response.addCookie(compCookie);
        response.addCookie(mouseCookie);
        response.addCookie(keyCookie);
    }
}
