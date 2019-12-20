package com.tan.springcloud2producer.test;

public class AssertTest {

    public static void main(String[] args) {
        boolean isOk = 1 > 2;
        try {
            assert isOk : "程序错误";
            System.out.println("程序正常");
        } catch (AssertionError err) {
            System.out.println(err.getMessage());
        }
    }
}
