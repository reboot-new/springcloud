package com.tan.springcloud2producer.test;

import java.net.InetAddress;

public class lanDemo {
    // 全局变量
    public static String msg;
    public static String ip;
    public static String hostName;

    public static void main(String[] args) { // 程序入口点
        lanSend lSend;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();
            hostName = addr.getHostName().toString();
            msg = ip + "@" + hostName;
            lSend = new lanSend();
            // 加入组播，并创建线程侦听
            lSend.join();
            // 广播信息，寻找上线主机交换信息
            lSend.sendGetUserMsg();
            // 程序睡眠5秒
            Thread.sleep(1000*5);
            // 广播下线通知
            lSend.offLine();
        } catch (Exception e) {
            System.out.println("*****获取本地用户信息出错*****");
        }
    }
}