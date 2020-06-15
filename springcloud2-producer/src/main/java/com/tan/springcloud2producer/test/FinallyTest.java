package com.tan.springcloud2producer.test;

public class FinallyTest {
    public static void main(String[] args) {

        try{
//            int i = 3/0;
            return;
        }catch (Exception ex){
            System.out.println("catch");
        }finally {
            System.out.println("finally");
        }
        System.out.println("out");
        return;
    }
}
