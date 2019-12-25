package com.tan.springcloud2producer.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sends one message to a serial device
 */
public final class RxtxClient {

    static final String PORT = System.getProperty("port", "COM3");

    static Logger logger = LoggerFactory.getLogger(RxtxClient.class);

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(RxtxChannel.class)
                    .handler(new ChannelInitializer<RxtxChannel>() {
                        @Override
                        public void initChannel(RxtxChannel ch) throws Exception {
                            ch.pipeline().addLast(
//                                    new LineBasedFrameDecoder(32768),
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new RxtxClientHandler()
                            );
                        }
                    }).option(RxtxChannelOption.BAUD_RATE,9600);
            logger.info("=======------------==========");
            ChannelFuture f = b.connect(new RxtxDeviceAddress(PORT)).sync();
            f.channel().closeFuture().sync();
//            f.channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }
}