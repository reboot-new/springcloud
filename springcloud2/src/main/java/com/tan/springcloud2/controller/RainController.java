package com.tan.springcloud2.controller;

import com.tan.springcloud2.entity.Student;
import com.tan.springcloud2.service.RainfallServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hy999/rain")
@Api(value = "/", tags = "测试服务接口")
public class RainController {
    @Autowired
    private RainfallServiceImpl rainfallService;

    @RequestMapping("/get")
    @ApiOperation(value="获取雨量列表", httpMethod = "GET",
            notes="根据输入的名称进行的测试接口")
    public Object get() {
        Object res = rainfallService.getAllRain("10000001",2017,1);
        return res;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ApiOperation(value="测试接口", httpMethod = "GET",
            notes="根据输入的名称进行的测试接口")
    public Student GetStudent(){
        return new Student();
    }

    @RequestMapping("/getStcdData")
    @ApiOperation(value="获取雨量列表", httpMethod = "GET",
            notes="根据输入的名称进行的测试接口")
    public Object getStcdData() {
        Object res = rainfallService.getStcdData("60402069");
        return res;
    }

    @RequestMapping(value = "/list")
    public List<Student> GetStudents(){
        List<Student> list = new ArrayList<>();
        list.add(new Student(new Date(),"hello1",1,1.1));
        list.add(new Student(new Date(),"hello2",2,2.2));
        return list;
    }
}
