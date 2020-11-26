package com.tan.springcloud2producer.test;

import cn.hutool.core.thread.ThreadUtil;

public class VolatileOrderingDemo implements Runnable {

    public volatile Boolean k = false;

    @Override
    public void run(){

        System.out.println("线程开始");

        while (!k){
//            System.out.println(Thread.currentThread().getId()+"==================="+k);
//            ThreadUtil.sleep(1);
        }
        System.out.println("ending");
    }

    /**
     * 主干代码更新
     *
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        VolatileOrderingDemo v =new VolatileOrderingDemo();
//        Thread t = new Thread(v);
//        t.start();

//        new Thread(v).start();

        new Thread(v).start();
        Thread.sleep(1000);
        v.k = true;
        System.out.println("主进程结束");
    }
}
