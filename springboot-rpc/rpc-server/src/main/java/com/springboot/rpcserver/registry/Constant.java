package com.springboot.rpcserver.registry;

/**
 * @program: springboot-all
 * @description: zk相关常量
 * @author: wangtengke
 * @create: 2018-12-07
 **/
public interface Constant {
    int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";

}
