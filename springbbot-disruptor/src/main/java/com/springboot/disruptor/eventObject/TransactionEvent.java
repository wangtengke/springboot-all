package com.springboot.disruptor.eventObject;

import lombok.Data;

/**
 * @program: springboot-all
 * @description: 事件(Event)就是通过 Disruptor 进行交换的数据类型。
 * @author: wangtengke
 * @create: 2018-12-06
 **/
@Data
public class TransactionEvent {
    private long seq;
    private double amount;
    private long callNumber;

}
