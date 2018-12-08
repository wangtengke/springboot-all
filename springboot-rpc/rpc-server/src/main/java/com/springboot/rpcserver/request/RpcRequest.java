package com.springboot.rpcserver.request;

import lombok.Data;

/**
 * @program: springboot-all
 * @description: 定义Request
 * @author: wangtengke
 * @create: 2018-12-07
 **/
@Data
public class RpcRequest {
    private String requestId;
    private String interfaceName;
    private String serviceVersion;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

}
