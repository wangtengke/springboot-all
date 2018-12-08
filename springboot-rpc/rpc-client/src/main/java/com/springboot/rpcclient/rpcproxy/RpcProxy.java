package com.springboot.rpcclient.rpcproxy;

import com.springboot.rpcclient.NettyClient;
import com.springboot.rpcserver.registry.ServiceDiscovery;
import com.springboot.rpcserver.registry.ZooKeeperDiscovery;
import com.springboot.rpcserver.request.RpcRequest;
import com.springboot.rpcserver.response.RpcResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @program: springboot-all
 * @description: 客户端rpc调用代理
 * @author: wangtengke
 * @create: 2018-12-08
 **/
@Component
public class RpcProxy {
    private String serverAddress;
    private ZooKeeperDiscovery serviceDiscovery = new ZooKeeperDiscovery("");

//    public RpcProxy(String serverAddress) {
//        this.serverAddress = serverAddress;
//    }
//
//    public RpcProxy(ZooKeeperDiscovery serviceDiscovery) {
//        this.serviceDiscovery = serviceDiscovery;
//    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest request = new RpcRequest(); // 创建并初始化 RPC 请求
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);

                        if (serviceDiscovery != null) {
                            serverAddress = serviceDiscovery.discover(); // 发现服务
                        }

                        String[] array = serverAddress.split(":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);

                        NettyClient client = new NettyClient(); // 初始化 RPC 客户端
                        RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应

                        if (response.getException() != null) {
                            throw response.getException();
                        } else {
                            return response.getResult();
                        }
                    }
                }
        );
    }
}
