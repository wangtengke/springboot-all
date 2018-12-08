package com.springboot.rpcclient;

import com.springboot.rpcclient.rpcproxy.RpcProxy;
import com.springboot.rpcserver.interfaces.helloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcClientApplication implements CommandLineRunner {
    @Autowired
    public RpcProxy rpcProxy;
    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        helloService helloService = rpcProxy.create(helloService.class);
        String result = helloService.hello("World");
    }
}
