package com.springboot.rpcserver.response;

import lombok.Data;

/**
 * @program: springboot-all
 * @description: 定义RpcResponse
 * @author: wangtengke
 * @create: 2018-12-07
 **/
@Data
public class RpcResponse {
    private String requestId;
    private Exception exception;
    private Object result;
}
