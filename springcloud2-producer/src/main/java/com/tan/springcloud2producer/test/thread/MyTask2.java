package com.tan.springcloud2producer.test.thread;

public class MyTask2 implements Runnable {

    private String cmd = "";

    public MyTask2(String _cmd){
        this.cmd = _cmd;
    }

    @Override
    public void run() {
        System.out.println(cmd);
    }
}
