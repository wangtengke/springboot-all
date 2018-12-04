package com.activemq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @program: springboot-all
 * @description: 消息消费者
 * @author: wangtengke
 * @create: 2018-12-03
 **/
@Component
@Slf4j
public class Consumer {
    @JmsListener(destination = "sample.queue")
    @SendTo("callback")
    public String receiveQueue(String text, Session session) throws JMSException {
        try {
            String text1 = text + "1";
            log.info(text1);

            return "callback" + text1;
        }
        catch (Exception e){
            session.recover();
            return "error";
        }

    }

//    @JmsListener(destination = "sample.queue")
    public void receiveQueue2(String text) {
        String text2 = text + "1";
        System.out.println(text + " 2");
    }

    @JmsListener(destination = "topic",containerFactory = "topicContainerFactory1")
    @SendTo("callback")
    public String receiveTopic(String text) {
        System.out.println(text + " 1");
        return "callback topic 1";
    }

    @JmsListener(destination = "topic",containerFactory = "topicContainerFactory2")
    @SendTo("callback")
    public String receiveTopic2(String text) {
        System.out.println(text + " 2");
        return "callback topic 2";
    }
}
