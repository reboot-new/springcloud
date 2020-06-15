package com.tan.springcloud2producer.test.thread;

import cn.hutool.core.thread.ThreadUtil;

public class SynchronizedTest {

    public synchronized void print1(){

        System.out.println("print1开始===================");
        System.out.println(Thread.currentThread().getId());
        ThreadUtil.sleep(1000 *2);
        System.out.println("print1结束===================");
    }

    public synchronized void print2(){

        System.out.println("print2开始===================");
        System.out.println(Thread.currentThread().getId());
        ThreadUtil.sleep(1000 *2);
        System.out.println("print2结束===================");
    }



    public static void main(String[] args) throws Exception {

        SynchronizedTest s = new SynchronizedTest();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s.print1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                s.print2();
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}
