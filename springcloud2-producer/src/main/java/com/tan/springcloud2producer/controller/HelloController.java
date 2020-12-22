package com.tan.springcloud2producer.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.elitel.license.LicenseController;
import com.tan.springcloud2producer.entity.ReponseEntity;
import com.tan.springcloud2producer.entity.Student;
import com.tan.springcloud2producer.entity.WaresCategory;
import com.tan.springcloud2producer.entity.WaresCategoryEnum;
import com.tan.springcloud2producer.helper.HttpHelper;
import com.tan.springcloud2producer.helper.RestUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    RestTemplate restTemplate;

//    public static String PRODUCT_SEARCCH_URL = "http://mproxy-search.jd.local/";
public static String PRODUCT_SEARCCH_URL = "http://127.0.0.1:8091/hello/gettest/";


    @RequestMapping("/index")
    public Object index(HttpServletRequest request, HttpServletResponse response) {
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
        System.out.println("IP:" + ip);

        String agent = request.getHeader("user-agent");

        System.out.println("Agent:" + agent);

        Object obj = request.getSession().getAttribute("setkey");
        if (obj == null) {
            System.out.println("session未空！");
            request.getSession().setAttribute("setkey", "hello");
        }
        // 创建cookie对象
        Cookie compCookie = new Cookie("computer", "HP");
        // 服务器把cookie响应给客户端，所有的cookie对象，都会在服务器端创建，通过http响应给客户端(浏览器)
        Cookie keyCookie = new Cookie("key", "doubleflybird");
        Cookie mouseCookie = new Cookie("mouse", "leishe");
        //如果不设置使用时间，那么将取不到Cookie的值
        mouseCookie.setMaxAge(60 * 60 * 24 * 30);
        System.out.println(request.getContextPath());// 项目名(在tomcat中部署的项目名)
        // /zzsxt/shopping.jsp
        keyCookie.setPath(request.getContextPath() + "/getCookie.sxt");// 一旦设置了cookie的路径，就只能通过这一个路径才能获取到cookie信息
        response.addCookie(compCookie);
//        response.addCookie(mouseCookie);
//        response.addCookie(keyCookie);
//        return ip;

        ReponseEntity r = new ReponseEntity();
        r.getWaresCategory().add(new WaresCategory(WaresCategoryEnum.HOT.getType(), WaresCategoryEnum.HOT.getName()));
        r.getWaresCategory().add(new WaresCategory(WaresCategoryEnum.ALL.getType(), WaresCategoryEnum.ALL.getName()));
        System.out.println(JSON.toJSONString(r));
        return r;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String testPost(HttpServletRequest request) throws Exception {

        String body = HttpHelper.getBodyString(request);
        return body;
    }


    @RequestMapping("/get")
    public Object getStudent() {

        String url =PRODUCT_SEARCCH_URL + "?key={key}&pagesize={pagesize}&page={page}&area_ids={area_ids}" +
                "&warehouse_id={warehouse_id}&delivery_id={delivery_id}&site_id={site_id}&leader_id={leader_id}" +
                "&client={client}&charset={charset}&urlencode={urlencode}&filt_type={filt_type}&pvid={pvid}&logid={logid}";

        Map<String, String> parmMap = new HashMap<String, String>();

        parmMap.put("key", StrUtil.format("virtual_cat_id,,{}", "2000057"));
        parmMap.put("area_ids", "1,2810,51081,0");
        parmMap.put("pagesize", "10");
        parmMap.put("page", "1");
        parmMap.put("warehouse_id", "978");//仓ID
        parmMap.put("delivery_id", "6");//配送中心ID
        parmMap.put("site_id", "389298716440145920");//自提点
        parmMap.put("leader_id", "389298716440145920");//团长ID
        parmMap.put("client", "1607347112371");
        parmMap.put("charset", "utf8");
        parmMap.put("urlencode", "no");
        parmMap.put("pvid", "");
        parmMap.put("logid", "");
        parmMap.put("filt_type", "sale_state_filter;productext,b12v0"); //过滤掉不可售和测试的商品

        String forObject = restTemplate.getForObject(url, String.class, parmMap);
        return forObject;
    }

    @RequestMapping("/log")
    public String testLog() {

        String message = null;
        try {
            message.toString();
        } catch (Exception ex) {
            logger.error("测试空指针异常", ex);
        }
        return message;
    }

    @RequestMapping("/rpc")
    public String testRpc() {

        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("right", 55);
        parmMap.put("left", 111);
        String result3 = HttpUtil.get("http://127.0.0.1:8092/test/get", parmMap);
        System.out.println(result3);
        return "ok";
    }

    @RequestMapping("/gettest")
    public String testGet() throws Exception{
        System.out.println("测试-=====================================================");

        Thread.sleep(1000*2);
        return "hello";
    }

    @Autowired
    private RestUtil restUtil;
}
