package com.tan.springcloud2producer.test.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

// 该handler是InboundHandler类型
public class HelloServerInHandler extends ChannelInboundHandlerAdapter {

    String clientIP;
    int clientPort;

    Logger logger = LoggerFactory.getLogger(HelloServerInHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        clientIP = ipSocket.getAddress().getHostAddress();
        clientPort = ipSocket.getPort();
        logger.info("客户端【连接】ip地址：{}:{}",clientIP,clientPort);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        logger.info(this.toString());
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(result1);
        String resultStr = new String(result1);
        // 接收并打印客户端的信息
        System.out.println("Client said:" + resultStr);
        // 释放资源，这行很关键
        result.release();

        // 向客户端发送消息
        String response = "I am ok!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端【断开】ip地址：{}:{}",clientIP,clientPort);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        super.exceptionCaught(ctx, cause);
    }
}
