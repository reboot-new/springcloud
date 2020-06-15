package com.tan.springcloud2producer.test.proxy;

public class StaticProxy implements IHello {
    private IHello ihello;

    public StaticProxy(IHello hello) {
        this.ihello = hello;
    }

    private void before() {
        System.out.println("static proxy before ...");
    }

    private void after() {
        System.out.println("static proxy after ...");
    }

    @Override
    public void say(String name) {
        before();
        ihello.say(name);
        after();
    }

    @Override
    public void add() {
        say("add");
    }
}
