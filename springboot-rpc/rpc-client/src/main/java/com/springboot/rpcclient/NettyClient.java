package com.springboot.rpcclient;

import com.springboot.rpcclient.handler.NettyClientHandler;
import com.springboot.rpcserver.config.nettycoder.Job;
import com.springboot.rpcserver.config.nettycoder.ObjectDecoder;
import com.springboot.rpcserver.config.nettycoder.ObjectEncoder;
import com.springboot.rpcserver.config.nettyconfig.NettyConfig;
import com.springboot.rpcserver.request.RpcRequest;
import com.springboot.rpcserver.response.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NettyClient {
    @Autowired
    private NettyConfig nettyConfig;

    private RpcResponse response;

    public void start() {
        int port = nettyConfig.getPort();
        String address = nettyConfig.getAddress();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup) // (2)
                    .channel(NioSocketChannel.class) // (3)
                    .option(ChannelOption.SO_KEEPALIVE, true) // (4)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
//                    .addLast(new StringDecoder())
//                    .addLast(new StringEncoder())
//                    .addLast("decoder",new TimeDecoder())
//                    .addLast("encoder",new TimeEncoder())
                                    .addLast(new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS))
                                    .addLast(new ObjectDecoder<Job>(Job.class))
                                    .addLast(new ObjectEncoder<Job>(Job.class))
                                    .addLast(new NettyClientHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(address, port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
//            System.out.println("try finish");
        } catch (InterruptedException e) {
            log.error("client启动失败");
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();

        }
    }

    public RpcResponse send(RpcRequest request) throws Exception {
        int port = nettyConfig.getPort();
        String address = nettyConfig.getAddress();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new ObjectEncoder<>(RpcRequest.class)) // 将 RPC 请求进行编码（为了发送请求）
                                    .addLast(new ObjectDecoder<>(RpcResponse.class)) // 将 RPC 响应进行解码（为了处理响应）
                                    .addLast(new NettyClientHandler()); // 使用 RpcClient 发送 RPC 请求
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.connect(address, port).sync();
            future.channel().writeAndFlush(request).sync();


            if (response != null) {
                future.channel().closeFuture().sync();
            }
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }
}
