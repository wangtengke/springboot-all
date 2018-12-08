package com.springboot.rpcserver.interfaces;

/**
 * @program: springboot-all
 * @description:
 * @author: wangtengke
 * @create: 2018-12-07
 **/
public class helloServiceImpl implements helloService {


    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }
}
