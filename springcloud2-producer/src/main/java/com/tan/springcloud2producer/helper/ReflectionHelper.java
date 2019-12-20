package com.tan.springcloud2producer.helper;

import com.google.common.base.Stopwatch;
import com.tan.springcloud2producer.test.classHelper.ReworkInterface;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class ReflectionHelper {
    public static void main(String[] args)  throws Exception {
        Reflections reflections = new Reflections("");
        Stopwatch stopwatch   = Stopwatch.createStarted();
        Set<Class<? extends ReworkInterface>> allTypes    = reflections.getSubTypesOf(ReworkInterface.class);
        System.out.println(stopwatch.toString());
        for (Class type : allTypes) {
            System.out.println(type.getName());
            Object object = type.newInstance();
            Method process = type.getMethod("Process");
            System.out.println(process.invoke(object));
        }
    }
}
