package com.tan.springcloud2producer.test.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RxtxClientHandler extends SimpleChannelInboundHandler<String> {

    Logger logger  = LoggerFactory.getLogger(RxtxClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //AT命令
        String str = "AT\r\n";
        ctx.writeAndFlush(str);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        if ("OK".equals(msg)) {
            System.out.println("Serial port responded to AT");
        } else {
            System.out.println("Serial port responded with not-OK: " + msg);
        }
//        ctx.close();
    }
}