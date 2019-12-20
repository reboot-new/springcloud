package com.tan.springcloud2producer.test.classHelper;

import com.tan.springcloud2producer.helper.JarHelper;
import org.apache.tomcat.Jar;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

public class LoadInvokeTest {
    public static void main(String[] args) throws Exception {

        URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:E:\\Elitel\\01_基础平台研发部\\01_svn\\05微服务框架\\01_主干源码\\02_el-baseframe\\base-frame\\main-frame-common\\target\\main-frame-common-1.0-SNAPSHOT.jar")},Thread.currentThread().getContextClassLoader());
        Class demo = classLoader.loadClass("com.elitel.common.DateUtilFormat");
        System.out.println(demo.getMethod("dataToString", Date.class).invoke(demo,new Date()));
//        JarHelper.loadJar("F:\\Code\\Java\\springcloud\\springcloud2\\target\\springcloud2-0.0.1-SNAPSHOT.jar");
//        JarHelper.loadJar("F:\\Download\\GoogleChrome下载\\commons-lang3-3.9.jar");
//        Class<?> aClass = Class.forName("com.tan.springcloud2.controller.HelloController");
//        Object instance = aClass.newInstance();
//        Object strip = aClass.getDeclaredMethod("Index").invoke(instance);
//        System.out.println(strip);

//        Class<?> aClass = Class.forName("org.apache.commons.lang3.StringUtils");
//        Object instance = aClass.newInstance();
//        Object strip = aClass.getDeclaredMethod("strip", String.class, String.class).invoke(instance, "[1,2,3,4,5,6,2,3,5,1]", "[]");
//        System.out.println(strip);
    }
}
