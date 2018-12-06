package com.springboot.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.springboot.disruptor.eventObject.TransactionEvent;

/**
 * @program: springboot-all
 * @description: 交易流水入库操作
 * @author: wangtengke
 * @create: 2018-12-06
 **/
public class InnerDBHandler implements EventHandler<TransactionEvent>, WorkHandler<TransactionEvent> {


    @Override
    public void onEvent(TransactionEvent event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(TransactionEvent event) throws Exception {
        event.setSeq(event.getSeq()*10000);
        System.out.println("拦截入库流水号------------  "+event.getSeq());
    }
}
