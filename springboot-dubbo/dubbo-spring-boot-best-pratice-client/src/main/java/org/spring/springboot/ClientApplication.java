package org.spring.springboot;

import org.spring.springboot.dubbo.CityDubboConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/** 
* @Description: client端 
* @Param:  
* @return:  
* @Author: Wtk 
* @Date: 2018/9/29 
*/ 
// Spring Boot 应用的标识
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ConfigurableApplicationContext run = SpringApplication.run(ClientApplication.class, args);
        CityDubboConsumerService cityService = run.getBean(CityDubboConsumerService.class);
        cityService.printCity();
    }
}