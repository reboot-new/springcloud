package com.tan.springcloud2producer.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {

        FilterPhoneFuction phoneFuction = (phone)->{
            System.out.println(phone);
            return true ;
        };

        hancle(phoneFuction);

//        Vector<Thread> vectors=new Vector<Thread>();
//        //启用5个线程
//        for(int i=1;i<=5;i++){
//            Thread childrenThread=new Thread(new Runnable(){
//                public void run(){
//                    try {
//                        System.out.println("子线程开始执行");
//                        Thread.sleep(1000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("子线程执行！");
//                }
//            });
//            vectors.add(childrenThread);
//            childrenThread.start();
//        }
//        //主线程
//        for(Thread thread : vectors){
//            thread.join(); //使用join来保证childrenThread的5个线程都执行完后，才执行主线程
//        }
        System.out.println("主线程执行！");
    }

    private static void hancle(FilterPhoneFuction phoneFuction) {
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc") ;
//        list.stream().forEach(phoneFuction::filter);

        for (String item:list
             ) {
            phoneFuction.filter(item);
        }
//        List<String> phoneList = readTxtFile("");//获取手机号
    }

    /**
     * 主干测试
     */
    /**
     * 分支2更新未提交
     */
    @FunctionalInterface
    public interface  FilterPhoneFuction {
        boolean filter(String phone);

        default String getInfo(){
            return "过滤手机号函数" ;
        }
    }
}
