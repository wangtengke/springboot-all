package com.springboot.disruptor.consumer;

import com.lmax.disruptor.EventTranslator;
import com.springboot.disruptor.eventObject.TransactionEvent;

/**
 * @program: springboot-all
 * @description: 事件处理类-交易流水初始化
 * @author: wangtengke
 * @create: 2018-12-06
 **/
public class AmountTrasfer implements EventTranslator<TransactionEvent> {
    @Override
    public void translateTo(TransactionEvent event, long sequence) {
        event.setAmount(Math.random()*99);
        event.setCallNumber(17088888888L);
        event.setSeq(System.currentTimeMillis());
        System.out.println("设置交易流水:"+event.getSeq());
    }
}
