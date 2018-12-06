package com.springboot.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.springboot.disruptor.eventObject.TransactionEvent;

/**
 * @program: springboot-all
 * @description: 消费者–定义事件处理的具体实现
 *                 拦截交易流水
 * @author: wangtengke
 * @create: 2018-12-06
 **/
public class TransHandler implements EventHandler<TransactionEvent>, WorkHandler<TransactionEvent> {


    @Override
    public void onEvent(TransactionEvent event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(TransactionEvent event) throws Exception {
        System.out.println("交易流水号为："+event.getSeq()+"||交易金额为:"+event.getAmount());
    }
}
