package com.tan.springcloud2producer.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.tan.springcloud2producer.helper.HttpHelper;
import com.tan.springcloud2producer.helper.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    public String post() throws Exception{
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String body = HttpHelper.getBodyString(request);
        String bodyJson = JsonHelper.pareByUrl(body);
        System.out.println(bodyJson);
        return "ok";
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String get(String left,String right){
        return "get:" + left + right;
    }

    /**
     * 测试请求等待
     * @return
     */
    @RequestMapping("/wait")
    public String waitTest() throws Exception{
        logger.info("开始了");
        Thread.sleep(1000*30);
        logger.info("结束了");
        return "";
    }
}
