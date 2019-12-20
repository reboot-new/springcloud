package com.tan.springcloud2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return restTemplate.getForEntity("http://apollo-configservice/info", String.class).getBody();
    }
}
