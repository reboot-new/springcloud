package com.tan.springcloud2producer.test.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

public class Person implements
        BeanFactoryAware, BeanNameAware,
        InitializingBean, DisposableBean
{

    private String name;
    private String address;
    private int phone;

    private BeanFactory beanFactory;
    private String beanName;

    public Person() {
        System.out.println("【构造器】调用Person的构造器实例化");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】注入属性name");
        this.name = name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.out.println("【注入属性】注入属性address");
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        System.out.println("【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person [address=" + address + ", name=" + name + ", phone="
                + phone + "]";
    }

    // 这是BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out
                .println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
        this.beanFactory = arg0;
    }

    // 这是BeanNameAware接口方法
    @Override
    public void setBeanName(String arg0) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
        this.beanName = arg0;
    }

    // 这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out
                .println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    // 这是DiposibleBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用DiposibleBean.destory()");
    }

    // 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestory() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

//    @Override
//    public Object postProcessBeforeInstantiation(Class<?> aClass, String s) throws BeansException {
//        System.out.println("【postProcessBeforeInstantiation】");
//        return null;
//    }
//
//    @Override
//    public boolean postProcessAfterInstantiation(Object o, String s) throws BeansException {
//        System.out.println("【postProcessAfterInstantiation】");
//        return false;
//    }
//
//    @Override
//    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors, Object o, String s) throws BeansException {
//        System.out.println("【postProcessPropertyValues】");
//        return propertyValues;
//    }

//    @Override
//    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
//        System.out.println("【postProcessBeforeInitialization】");
//        return o;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
//        System.out.println("【postProcessAfterInitialization】");
//        return o;
//    }
}