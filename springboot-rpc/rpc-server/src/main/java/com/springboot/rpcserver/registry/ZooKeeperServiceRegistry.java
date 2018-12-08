package com.springboot.rpcserver.registry;


import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: springboot-all
 * @description: 连接ZK注册中心，创建服务注册目录
 * @author: wangtengke
 * @create: 2018-12-07
 **/
@Slf4j
@Component
public class ZooKeeperServiceRegistry{

    private CountDownLatch latch = new CountDownLatch(1);

    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer(data);
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

    private ZooKeeper connectServer(String registryAddress) {
        ZooKeeper zk = null;
        try {
            // todo zookeeper启动有问题， session超时无效 待解决
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
//                    // 判断是否已连接ZK,连接后计数器递减.
//                    if (event.getState() == Event.KeeperState.SyncConnected) {
//                        latch.countDown();
//                    }
                }
            });

            // 若计数器不为0,则等待.
//            latch.await();
        } catch (IOException e) {
            log.error("", e);
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }
}
