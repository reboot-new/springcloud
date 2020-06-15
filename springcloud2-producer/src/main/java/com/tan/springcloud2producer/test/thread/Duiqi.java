package com.tan.springcloud2producer.test.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Duiqi {

    private static class T{
        public volatile long x = 0L;
        public long x1,x2,x3,x4,x5,x6,x7;
//        public long x1,x2,x3,x4,x5,x6;
//        public long x1,x2,x3;
    }

    public static T[] la = new T[2];

    static {
        la[0] = new T();
        la[1] = new T();
    }

    public static void main(String[] args) throws Exception {

        List<Long> ll = new ArrayList<>();

        long sum = 0;

        for (int i = 0; i < 100 ;++i){
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (long i = 0;i < 1000_0000L;++i){
                        la[0].x = i;
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (long i = 0;i < 1000_0000L;++i){
                        la[1].x = i;
                    }
                }
            });


            TimeInterval timer = DateUtil.timer();

            t1.start();
            t2.start();
            t1.join();
            t2.join();


            Long l = timer.interval();
            sum += l;
//            System.out.println(timer.interval());
        }

        System.out.println(sum / 100);

    }
}
