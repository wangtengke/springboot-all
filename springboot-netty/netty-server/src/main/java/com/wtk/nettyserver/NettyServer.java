package com.wtk.nettyserver;

import com.wtk.nettyconfig.nettycoder.JobDecoder;
import com.wtk.nettyconfig.nettycoder.JobEncoder;
import com.wtk.nettyconfig.nettyconfig.NettyConfig;
import com.wtk.nettyserver.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: netty
 * @description:
 * @author: WangTengKe
 * @create: 2018-11-11
 **/
@Component
public class NettyServer {
    @Autowired
    private NettyConfig nettyConfig;

    public void start() throws Exception {
        int port = nettyConfig.getPort();
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
//                            .addLast(new StringDecoder())
//                            .addLast(new StringEncoder())
                                    .addLast(new IdleStateHandler(4, 0, 0, TimeUnit.SECONDS))
//                            .addLast(new IdleServerHandler())
                                    .addLast(new JobDecoder())
                                    .addLast(new JobEncoder())
                                    .addLast(new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)


            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8082;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new NettyServer().start();
    }
}
