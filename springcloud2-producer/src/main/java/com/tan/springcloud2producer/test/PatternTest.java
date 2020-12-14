package com.tan.springcloud2producer.test;

import com.alibaba.fastjson.JSON;
import com.tan.springcloud2producer.entity.Student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {

        Student s = null;

        System.out.println(JSON.toJSONString(s));

        System.out.println(JSON.toJSONString(new Student()));

        System.out.println("============");

        String str = "##stcd##";

        // 根据以前的方法进行判断
        // System.out.println(str.equals("abc"));

        /**
         * 用正则表达式来判断
         * 1.compile(String regex)    将给定的正则表达式编译到模式中。
         * 2.matcher(CharSequence input)    创建匹配给定输入与此模式的匹配器。
         * 3.matches()    尝试将整个区域与模式匹配。
         */
        // 首先要编译正则规则形式


        String[] splitArray = str.split("(?m)^##(.*)##$");

        Pattern p = Pattern.compile("^##(.*)##$");
        // 将正则进行匹配
        Matcher m = p.matcher(str);
        // 进行判断
        boolean b = m.matches();
        System.out.println(b);
    }

}
