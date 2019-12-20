package com.tan.springcloud2.effective.Chapter2;

import java.util.Date;

public class Person {
    private int age;
    private double heigh;
    private String name;
    private Date birth;
    protected Person(Builder builder){
        this.age = builder.getAge();
        this.heigh = builder.getHeigh();
        this.name = builder.getName();
        this.birth = builder.getBirth();
    }


    public static class Builder{
        private int age;
        private double heigh;
        private String name;
        private Date birth;

        public int getAge() {
            return age;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public double getHeigh() {
            return heigh;
        }

        public Builder setHeigh(double heigh) {
            this.heigh = heigh;
            return this;
        }

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Date getBirth() {
            return birth;
        }

        public Builder setBirth(Date birth) {
            this.birth = birth;
            return this;
        }

        public Builder(String name){
            this.name = name;
        }

        public Person build(){
            return new Person(this);
        }
    }

    public static void main(String[] args) {
        Person p = new Person.Builder("tan_alpha")
                .setAge(12)
                .setBirth(new Date())
                .setHeigh(165.5).build();
        System.out.println(p);
    }
}
