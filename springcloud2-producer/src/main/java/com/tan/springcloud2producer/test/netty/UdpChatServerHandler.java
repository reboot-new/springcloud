package com.tan.springcloud2producer.test.netty;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息处理类
 *
 * @author kokJuis
 * @version 1.0
 * @date 2016-9-30
 */
public class UdpChatServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    Logger logger = LoggerFactory.getLogger(UdpChatServerHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //注意，UDP的通道至始至终只有一个，关了就不能接收了。
        System.out.println("UDP通道已经连接");
        UdpChatServer.ctx = ctx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket
            packet) throws Exception {
        logger.info(this.toString());
        System.out.println("消息来源"  + packet.sender().getHostString() +":"+ packet.sender().getPort());

        // 消息处理。。。。。

        byte[] result1 = new byte[packet.content().readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        packet.content().readBytes(result1);
        String resultStr = new String(result1);
        logger.info(resultStr);
        //消息发送。。。。
//        ThreadUtil.sleep(1000*10);
//        DatagramPacket dp = new DatagramPacket(Unpooled.copiedBuffer("消息".getBytes()), packet.sender());
//        UdpChatServer.channel.writeAndFlush(dp);
    }
}
