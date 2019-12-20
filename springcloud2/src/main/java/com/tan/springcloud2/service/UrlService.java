package com.tan.springcloud2.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "product-url", url = "http://127.0.0.1:8092")
public interface UrlService {
    @RequestMapping(value = "/hello/index",method = RequestMethod.GET)
    String test();
}
