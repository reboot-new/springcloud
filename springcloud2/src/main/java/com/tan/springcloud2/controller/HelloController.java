package com.tan.springcloud2.controller;

import com.elitel.redis.annotation.RedisCache;
import com.elitel.redis.annotation.RedisEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private Logger logger =  LoggerFactory.getLogger(HelloController.class);

//    @Autowired
//    private RainfallService rainfallService;

    @RequestMapping("index")
//    @RedisCache(value = "hello",key = "'Index'", expired = "300", update = "#update")
    public Date Index(){
        Map<String,String> map  = new HashMap<>(7);
        System.out.println("hello springcloud2 ..");
//        List<Student> list = new ArrayList<Student>();
//        list.add(new Student(new Date(),"Tom",3,4.3));
//        list.add(new Student(new Date(),"To33m",3,4.3));
//        return list;
//        rainfallService.getAllRain("",1,1);
        return  new Date();
    }

    @RequestMapping("clean")
    @RedisEmpty(value = "hello*")
    public String Clean(){
        return "clean";
    }

    @RequestMapping(value = "/fetch/{id} ", method = RequestMethod.GET)
    String getDynamicUriValue(@PathVariable String id) {
        System.out.println("ID is  " + id);
        return "Dynamic URI parameter fetched ";
    }

    @RequestMapping(value = "/fetch/{id:[a-z]+}/{name}", method = RequestMethod.GET)
    public String getDynamicUriValueRegex(@PathVariable("name") String name) {
        System.out.println("Name is  " + name);
        return "Dynamic URI parameter fetched using regex " + name;
    }
}
