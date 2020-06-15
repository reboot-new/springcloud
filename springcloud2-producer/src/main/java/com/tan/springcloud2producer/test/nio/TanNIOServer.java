package com.tan.springcloud2producer.test.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TanNIOServer {



    public static void main(String[] args) throws Exception {
        /*
        * 1、创建一个本地端口监听端口的channel
        * 2、设置非阻塞
        * 3、创建select
        * 4、绑定channel到select，并且注册连接事件
        * */

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(9000));


        Selector selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            //监听等待事情发生
            System.out.println("等待事情发生");
            int count =  selector.select();
            System.out.println("有事情发生了");
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                handle(key);
            }
        }
    }

    public static void handle(SelectionKey key) throws Exception{
        /*
        * 1、通过key获取事件类型
        * 2、获取客户端的channel
        * 3、通过channel获取发送的消息
        * */
        if(key.isAcceptable()){
            System.out.println("有新的客户端连接了");
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);

            sc.register(key.selector(),SelectionKey.OP_READ);

        }else if(key.isReadable()){
            System.out.println("有客户端发消息了");
            SocketChannel sc =  (SocketChannel)key.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //获取发送的消息.
            int len = sc.read(buffer);
            if (len >= 0){
                System.out.println("接收"+new String(buffer.array(),0,len));
            }
            ByteBuffer writeBuffer = ByteBuffer.wrap("你好客户端".getBytes());
            sc.write(writeBuffer);

            key.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        }else if (key.isWritable()){
            System.out.println("服务端发送消息了");
            SocketChannel sc = (SocketChannel) key.channel();
            key.interestOps(SelectionKey.OP_READ);
        }
    }

}
