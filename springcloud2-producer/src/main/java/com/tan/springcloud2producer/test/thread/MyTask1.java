package com.tan.springcloud2producer.test.thread;

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTask1 implements Runnable {

    private Logger logger = LoggerFactory.getLogger(MyTask1.class);

    private String myName;

    public MyTask1(String name){
        myName = name;
    }

    @Override
    public void run(){
        try {
            ThreadUtil.sleep(1000);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+myName+Thread.currentThread().getId());
            if (logger == null){
                System.out.println("logger wei null");
            }
            Thread.sleep(1000* 5);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
