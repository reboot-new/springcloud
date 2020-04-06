package com.tan.springcloud2producer.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

    public static Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    public static void main(String[] args) {

        String message = null;

        try {
            message.toString();
        }catch (Exception ex){
            logger.error("测试空指针异常",ex);
        }
    }
}
