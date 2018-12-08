package com.springboot.rpcserver.interfaces;

/**
 * @program: springboot-all
 * @description:
 * @author: wangtengke
 * @create: 2018-12-07
 **/
@RpcService(helloService.class)
public class helloService2Impl implements helloService {


    @Override
    public String hello(String name) {
        return "你好! " + name;
    }

}
