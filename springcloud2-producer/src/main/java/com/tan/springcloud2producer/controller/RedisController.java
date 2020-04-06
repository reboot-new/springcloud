package com.tan.springcloud2producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("set")
    public String SetKey(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        //1小时过期
        ops.set("test_key","test_value",1, TimeUnit.HOURS);
        return "ok";
    }
}
