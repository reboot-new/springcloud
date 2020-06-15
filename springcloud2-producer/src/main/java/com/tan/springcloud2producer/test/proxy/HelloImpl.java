package com.tan.springcloud2producer.test.proxy;

public class HelloImpl implements IHello {
    @Override
    public void say(String context) {
        System.out.println("HelloImpl:" + context);
    }

    public void add(){
        say("add方法");
    }
}
