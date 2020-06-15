package com.tan.springcloud2producer.test.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean
    public HelloImpl helloImpl(){
        return new HelloImpl();
    }

    @Bean
    public MyInstantiationAwareBeanPostProcessor myInstantiationAwareBeanPostProcessor(){
        return new MyInstantiationAwareBeanPostProcessor();
    }
}
