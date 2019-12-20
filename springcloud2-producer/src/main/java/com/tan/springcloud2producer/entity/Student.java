package com.tan.springcloud2producer.entity;

import java.io.Closeable;
import java.io.IOException;

public class Student implements AutoCloseable {
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Integer age;
    String name;

    public Student(){};

    public Student(String name,Integer age){
        this.name = name;
        this.age = age;
    }
    @Override
    public void close() throws Exception {
        Thread.sleep(1000 *10);
        System.out.println("closeseesese");
    }
}
