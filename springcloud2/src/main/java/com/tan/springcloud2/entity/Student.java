package com.tan.springcloud2.entity;

import java.util.Date;

public class Student {
    public Student(Date birth, String name, int age, double height) {
        this.birth = birth;
        this.name = name;
        this.age = age;
        this.height = height;
    }
    public Student(){}

    private Date birth;
    private String name;
    private int age;
    private double height;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
