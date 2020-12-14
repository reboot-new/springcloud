package com.tan.springcloud2producer.test.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MainConfig {

    @Bean(initMethod = "myInit",destroyMethod = "myDestory")
    public Person person(){
        return new Person();
    }

    @Bean
    public Person1 person1(){
        return new Person1();
    }

//    @Bean
    public MyInstantiationAwareBeanPostProcessor2 myInstantiationAwareBeanPostProcessor2(){
        return new MyInstantiationAwareBeanPostProcessor2();
    }

//    @Bean
//    public MyInstantiationAwareBeanPostProcessor1 myInstantiationAwareBeanPostProcessor1(){
//        return new MyInstantiationAwareBeanPostProcessor1();
//    }

}
