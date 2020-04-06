package com.tan.springcloud2producer.controller;


import com.elitel.license.LicenseController;
import com.tan.springcloud2producer.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hello")
@Lazy(false)
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
//        return "/hello/index";
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        System.out.println("IP:"+ip);

        String agent = request.getHeader("user-agent");

        System.out.println("Agent:"+agent);

        Object obj =  request.getSession().getAttribute("setkey");
        if (obj == null){
            System.out.println("session未空！");
            request.getSession().setAttribute("setkey","hello");
        }
        // 创建cookie对象
        Cookie compCookie = new Cookie("computer", "HP");
        // 服务器把cookie响应给客户端，所有的cookie对象，都会在服务器端创建，通过http响应给客户端(浏览器)
        Cookie keyCookie = new Cookie("key", "doubleflybird");
        Cookie mouseCookie = new Cookie("mouse", "leishe");
        //如果不设置使用时间，那么将取不到Cookie的值
        mouseCookie.setMaxAge(60*60*24*30);
        System.out.println(request.getContextPath());// 项目名(在tomcat中部署的项目名)
        // /zzsxt/shopping.jsp
        keyCookie.setPath(request.getContextPath()+"/getCookie.sxt");// 一旦设置了cookie的路径，就只能通过这一个路径才能获取到cookie信息
        response.addCookie(compCookie);
//        response.addCookie(mouseCookie);
//        response.addCookie(keyCookie);
//        return ip;
        return "/hello/index";
    }

    @RequestMapping("/get")
    public Object getStudent(){
//        List<Student> list = new ArrayList<>();
//        Student s1 = new Student();
//        s1.setAge(null);
//        s1.setName("nihao");
//        list.add(s1);

        //
        Map<String,String> map = new HashMap<>();
        map.put("name",null);
        return map;
    }

    @RequestMapping("/log")
    public String testLog(){

        String message = null;
        try {
            message.toString();
        }catch (Exception ex){
            logger.error("测试空指针异常",ex);
        }
        return message;
    }
}
