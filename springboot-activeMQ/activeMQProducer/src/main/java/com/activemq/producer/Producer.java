package com.activemq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @program: springboot-all
 * @description: 消息提供者
 * @author: wangtengke
 * @create: 2018-12-03
 **/
@Component
@Slf4j
public class Producer implements CommandLineRunner {

    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    @Override
    public void run(String... args) throws Exception {
        try {
            while (true){
                Thread.sleep(1000);
                send("Sample message");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void send(String msg) {
        log.info("producer send");
        this.jmsTemplate.convertAndSend(this.topic, msg + " topic");
//        this.jmsTemplate.convertAndSend(this.queue, msg + " queue");
    }

    @JmsListener(destination = "callback")
    public void callback(String msg) {
        log.info("producer recieve "+msg);
    }

}
