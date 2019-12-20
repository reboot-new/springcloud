package com.tan.springcloud2producer.test;

import com.tan.springcloud2producer.entity.Student;

public class CloseableTest {


    public static void main(String[] args) throws Exception {
        try(Student s = new Student()){
            s.setAge(12);
            System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("final");
        }
        System.out.println("方法结束");
        Thread.sleep(1000* 20);
    }
}
