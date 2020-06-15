package com.tan.springcloud2producer.test.spring.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLifeMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        System.out.println(ctx.getBean("person"));
        ctx.close();
    }
}
