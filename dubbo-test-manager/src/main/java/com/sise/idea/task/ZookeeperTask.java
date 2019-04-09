package com.sise.idea.task;

import com.sise.idea.handler.ZookeeperHandler;
import com.sise.idea.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.sise.idea.util.ParamUtil.getHost;
import static com.sise.idea.util.ParamUtil.getPort;

/**
 * @author idea
 * @data 2019/4/9
 */
@Slf4j
public class ZookeeperTask implements Runnable {

    @Autowired
    private ZookeeperService zookeeperService;

    private CountDownLatch taskStart;

    private CountDownLatch zkHandleIsNotNull;

    private static ZookeeperHandler zookeeperHandler;

    private static ZooKeeper zooKeeper;

    private AtomicBoolean connectStatus = new AtomicBoolean(false);

    public ZookeeperTask(String address, CountDownLatch taskStart, CountDownLatch zkHandleIsNotNull) {
        this.taskStart = taskStart;
        this.zkHandleIsNotNull = zkHandleIsNotNull;
        String host = getHost(address);
        int port = getPort(address);
        zookeeperHandler = new ZookeeperHandler(host, port);
    }

    public ZookeeperHandler getZookeeperHandler() {
        return zookeeperHandler;
    }

    public boolean isConnection() {
        return connectStatus.get() == true;
    }

    public ZooKeeper getZooKeeper(){
        return zooKeeper;
    }

    @Override
    public void run() {
        taskStart.countDown();
        log.error("[开启任务]");
        zkHandleIsNotNull.countDown();
        connectStatus.set(true);
    }
}
