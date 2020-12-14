package com.tan.springcloud2producer.test;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.RandomUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FileWriterTest {


    public static void main(String[] args) {

        BigDecimal a = new BigDecimal(10.2);
        BigDecimal b = new BigDecimal(10.2);
        System.out.println(a.compareTo(b));

        Map<String,String> map = new HashMap<>();
        map.put("22","3333");
        System.out.println(map.get("33"));
    }
}
