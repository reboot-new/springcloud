package com.tan.springcloud2producer.test.spring.bean;

import com.tan.springcloud2producer.test.proxy.CglibProxy;
import com.tan.springcloud2producer.test.proxy.HelloImpl;
import com.tan.springcloud2producer.test.proxy.ProxyConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.beans.PropertyDescriptor;

public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessBeforeInstantiation\n");
        //利用 其 生成动态代理
//        if(beanClass== HelloImpl.class){
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(beanClass);
//            enhancer.setCallback(new CglibProxy());
//            HelloImpl testFb = (HelloImpl)enhancer.create();
//            System.out.print("返回动态代理\n");
//            return testFb;
//        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessAfterInstantiation\n");

        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessPropertyValues\n");
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessBeforeInitialization\n");

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessAfterInitialization\n");

        return bean;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
//        HelloImpl hello =  ctx.getBean(HelloImpl.class);
//        hello.say("sayyyyyyyyyyyyyyyyy");
        ctx.close();
    }
}