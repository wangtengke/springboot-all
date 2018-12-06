package com.springboot.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.springboot.disruptor.eventObject.TransactionEvent;

/**
 * @program: springboot-all
 * @description: 发送验证短信
 * @author: wangtengke
 * @create: 2018-12-06
 **/
public class SendMsgHandler implements EventHandler<TransactionEvent> {

    @Override
    public void onEvent(TransactionEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("向手机号:"+event.getCallNumber()+"发送验证短信......");
    }
}
