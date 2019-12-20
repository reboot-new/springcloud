package com.tan.springcloud2producer.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * <p>项目名称：UDPMulticastDemo </p>
 * <p>类名称：lanDemo </p>
 * <p>类描述：   </p>
 * <p>创建人：wuzq，zhongqianhit@163.com </p>
 * <p>创建时间：2012-6-7 上午10:21:06 </p>
 * <p>修改人：wuzq，zhongqianhit@163.com </p>
 * <p>修改时间：2012-6-7 上午10:21:06 </p>
 * <p>修改备注：	</p>
 * @version
 **/

class lanSend {
    /**
     * 广播地址IP
     */
    private static final String BROADCAST_IP = "230.0.0.1";
    /**
     * 不同的port对应不同的socket发送端和接收端
     */
    private static final int BROADCAST_INT_PORT = 40005;

    /**
     * 用于接收广播信息
     */
    MulticastSocket broadSocket;
    /**
     * 数据流套接字 相当于码头，用于发送信息
     */
    DatagramSocket sender;
    /**
     * 广播地址
     */
    InetAddress broadAddress;

    public lanSend() {
        try {

            // 初始化
            broadSocket = new MulticastSocket(BROADCAST_INT_PORT);
            broadAddress = InetAddress.getByName(BROADCAST_IP);

            sender = new DatagramSocket();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****lanSend初始化失败*****" + e.toString());
        }
    }

    void join() {
        try {
            // 加入到组播地址，这样就能接收到组播信息
            broadSocket.joinGroup(broadAddress);
            // 新建一个线程，用于循环侦听端口信息
            new Thread(new GetPacket()).start();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****加入组播失败*****");
        }
    }

    /**
     * 广播发送查找在线用户
     */
    void sendGetUserMsg() {
        byte[] b = new byte[1024];
        // 数据包，相当于集装箱，封装信息
        DatagramPacket packet;
        try {
            b = ("find@" + lanDemo.msg).getBytes();
            // 广播信息到指定端口
            packet = new DatagramPacket(b, b.length, broadAddress,
                    BROADCAST_INT_PORT);
            sender.send(packet);
            System.out.println("*****已发送请求*****");
        } catch (Exception e) {
            System.out.println("*****查找出错*****");
        }
    }


    /**
     * 当局域网内的在线机子收到广播信息时响应并向发送广播的ip地址主机发送返还信息，达到交换信息的目的
     * @param ip
     */
    void returnUserMsg(String ip) {
        byte[] b = new byte[1024];
        DatagramPacket packet;
        try {
            b = ("retn@" + lanDemo.msg).getBytes();
            packet = new DatagramPacket(b, b.length, InetAddress.getByName(ip),
                    BROADCAST_INT_PORT);
            sender.send(packet);
            System.out.print("发送信息成功！");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****发送返还信息失败*****");
        }
    }

    /**
     * 当局域网某机子下线是需要广播发送下线通知
     */
    void offLine() {
        byte[] b = new byte[1024];
        DatagramPacket packet;
        try {
            b = ("offl@" + lanDemo.msg).getBytes();
            packet = new DatagramPacket(b, b.length, broadAddress,
                    BROADCAST_INT_PORT);
            sender.send(packet);
            System.out.println("*****已离线*****");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****离线异常*****");
        }
    }

    /**
     * 新建的线程，用于侦听
     */
    class GetPacket implements Runnable {
        @Override
        public void run() {
            DatagramPacket inPacket;

            String[] message;
            while (true) {
                try {
                    inPacket = new DatagramPacket(new byte[1024], 1024);
                    // 接收广播信息并将信息封装到inPacket中
                    broadSocket.receive(inPacket);
                    // 获取信息，并切割头部，判断是何种信息（find--上线，retn--回答，offl--下线）
                    message = new String(inPacket.getData(), 0,
                            inPacket.getLength()).split("@");

                    // 忽略自身
                    if (message[1].equals(lanDemo.ip)) {
                        continue;
                    }

                    if (message[0].equals("find")) { // 如果是请求信息
                        System.out.println("新上线主机：" + " ip：" + message[1]
                                + " 主机：" + message[2]);
                        returnUserMsg(message[1]);
                    } else if (message[0].equals("retn")) { // 如果是返回信息
                        System.out.println("找到新主机：" + " ip：" + message[1]
                                + " 主机：" + message[2]);
                    } else if (message[0].equals("offl")) { // 如果是离线信息
                        System.out.println("主机下线：" + " ip：" + message[1]
                                + " 主机：" + message[2]);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("线程出错 " + e);
                }
            }
        }
    }
}

