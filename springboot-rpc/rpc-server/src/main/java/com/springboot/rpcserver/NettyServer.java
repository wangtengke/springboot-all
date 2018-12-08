package com.springboot.rpcserver;


import com.springboot.rpcserver.config.nettycoder.*;
import com.springboot.rpcserver.config.nettyconfig.NettyConfig;
import com.springboot.rpcserver.handler.NettyServerHandler;
import com.springboot.rpcserver.interfaces.RpcService;
import com.springboot.rpcserver.registry.ZooKeeperServiceRegistry;
import com.springboot.rpcserver.request.RpcRequest;
import com.springboot.rpcserver.response.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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
    @Autowired
    private ZooKeeperServiceRegistry zooKeeperServiceRegistry;
    private Map<String, Object> handlerMap = new HashMap<String, Object>(); // 存放接口名与服务对象之间的映射关系
    public void start() throws Exception {

        int port = nettyConfig.getPort();
        String serverAddress = nettyConfig.getAddress();
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
                                    .addLast(new ObjectEncoder<>(RpcRequest.class))
                                    .addLast(new ObjectDecoder<>(RpcResponse.class))
                                    .addLast(new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)


            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
            if (zooKeeperServiceRegistry != null) {
                zooKeeperServiceRegistry.register(serverAddress); // 注册服务地址
            }


            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class); // 获取所有带有 RpcService 注解的 Spring Bean
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
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
