package com.tan.springcloud2producer.test.thinking.t5_3;


/**
 * 只能在构造函数中，只能通过this方法调用一次构造函数
 *
 */
public class Student {

    private int age;
    private String name;

    public Student(String name){
        this.name = name;
    }

    public Student(int age){
        this.age = age;
    }

    public Student(String name, int age){
        this(name);
//        this(age);
        this.age = age;
    }

    public static void main(String[] args) {
        Student s = new Student("tom",23);
    }
}
