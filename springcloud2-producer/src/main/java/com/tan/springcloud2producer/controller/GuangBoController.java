
package com.tan.springcloud2producer.controller;

import com.tan.springcloud2producer.test.lanDemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

@RestController
@RequestMapping("/lan")
public class GuangBoController {

    @RequestMapping("/start")
    public void start() {
        lanDemo.main(new String[1]);
    }

    @RequestMapping("/server")
    public void server() {
        Thread th = new MyThread();
        th.start();
    }

    @RequestMapping("/send")
    public void send() {
        //广播的实现 :由客户端发出广播，服务器端接收
        String host = "255.255.255.255";//广播地址
        int port = 9999;//广播的目的端口
        //用于发送的字符串
        String message = "test";
        try
        {
            InetAddress adds = InetAddress.getByName(host);
            DatagramSocket ds = new DatagramSocket();
            ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(message.getBytes(),message.length(), adds, port);
            ds.send(dp);
            ds.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class MyThread extends Thread {
        @Override
        public void run(){
            int port = 9999;//开启监听的端口
            DatagramSocket ds = null;
            DatagramPacket dp = null;
            byte[] buf = new byte[1024];//存储发来的消息
            StringBuffer sbuf = new StringBuffer();
            try
            {
                //绑定端口的
                ds = new DatagramSocket(port);
                System.out.println("监听广播端口打开：");

                while (true){
                    dp = new DatagramPacket(buf, buf.length);
                    sbuf = new StringBuffer();
                    ds.receive(dp);
                    System.out.println("客户端地址："+dp.getAddress());
//                    ds.close();
                    int i;
                    for(i=0;i<1024;i++) {
                        if(buf[i] == 0)
                        {
                            break;
                        }
                        sbuf.append((char) buf[i]);
                    }
                    System.out.println("收到广播消息：" + sbuf.toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
