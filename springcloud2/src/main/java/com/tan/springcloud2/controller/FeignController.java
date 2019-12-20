package com.tan.springcloud2.controller;

import com.tan.springcloud2.service.FeignService;
import com.tan.springcloud2.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feign")
public class FeignController {
    @Autowired
    FeignService feignService;

    @Autowired
    UrlService urlService;

    @RequestMapping("urlTest")
    public String urlTest(){
        return urlService.test();
    }

    @RequestMapping("/post")
    public String post(){
        Map<String,Object> p = new HashMap<>();
        p.put("key1","value1");
        p.put("key2",getDoubleList());
        p.put("key3",getStringMap());
        String res = feignService.post(p);
        return res;
    }

    @RequestMapping("/get")
    public String get(){
        String res = feignService.get("hello","world");
        return res;
    }

    private List<Double> getDoubleList(){
        List<Double> list = new ArrayList<Double>();
        list.add(3.1);
        list.add(3.2);
        return list;
    }

    private Map<String,String> getStringMap(){
        Map<String,String> map= new HashMap<>();
        map.put("key11","valu11");
        map.put("key12","valu12");
        return map;
    }
}
