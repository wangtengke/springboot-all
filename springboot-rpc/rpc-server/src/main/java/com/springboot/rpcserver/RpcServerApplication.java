package com.springboot.rpcserver;

import com.springboot.rpcserver.interfaces.helloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class RpcServerApplication implements CommandLineRunner{
    @Autowired
    NettyServer nettyServer;
    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }
}
