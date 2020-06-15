 package com.tan.springcloud2producer.test.mianshi;

import cn.hutool.core.thread.ThreadUtil;

public class ExangePrint {

    public static volatile  int k =0 ;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    if (ExangePrint.k%2 ==0){
                        System.out.println(ExangePrint.k);
                        ++ExangePrint.k;
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (ExangePrint.k%2 ==1){
                        System.out.println(ExangePrint.k);
                        ++ExangePrint.k;
                    }
                }
            }
        }).start();


        ThreadUtil.sleep(1000 * 1);
    }
}
