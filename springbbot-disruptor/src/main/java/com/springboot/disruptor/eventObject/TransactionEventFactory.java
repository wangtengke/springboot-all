package com.springboot.disruptor.eventObject;

import com.lmax.disruptor.EventFactory;
import com.springboot.disruptor.eventObject.TransactionEvent;

/**
 * @program: springboot-all
 * @description: Event Factory 定义了如何实例化前面第1步中定义的事件(Event)
 *                 Disruptor 通过 EventFactory 在 RingBuffer 中预创建 Event 的实例。
 *                 一个 Event 实例实际上被用作一个“数据槽”，发布者发布前，先从 RingBuffer 获得一个 Event 的实例，
 *                 然后往 Event 实例中填充数据，之后再发布到 RingBuffer中，之后由 Consumer 获得该 Event 实例并从中读取数据。
 * @author: wangtengke
 * @create: 2018-12-06
 **/
public class TransactionEventFactory implements EventFactory<TransactionEvent> {


    @Override
    public TransactionEvent newInstance() {
        return new TransactionEvent();
    }
}
