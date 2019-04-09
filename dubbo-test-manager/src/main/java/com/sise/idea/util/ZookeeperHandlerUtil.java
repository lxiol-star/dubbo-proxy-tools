package com.sise.idea.util;

import com.sise.idea.handler.ZookeeperHandler;
import com.sise.idea.task.WatchDogTask;
import com.sise.idea.task.ZookeeperTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 通过watchdog 确保拿到的zk链接是有效的
 *
 * @author idea
 * @data 2019/4/9
 */
@Slf4j
public class ZookeeperHandlerUtil {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private CountDownLatch zkHandleIsNotNull = new CountDownLatch(1);

    public ZookeeperHandler getZookeeperHandler(String address) {
        ZookeeperTask zookeeperTask = new ZookeeperTask(address, countDownLatch, zkHandleIsNotNull);
        Thread taskThread = new Thread(zookeeperTask);
        taskThread.start();
        try {
            countDownLatch.await();
            WatchDogTask watchDogTask = new WatchDogTask(taskThread, zookeeperTask, zookeeperTask.getZooKeeper());
            Thread watchDogThread = new Thread(watchDogTask);
            watchDogThread.start();
            zkHandleIsNotNull.await();
        } catch (InterruptedException e) {
            log.error("[ZookeeperHandlerUtil]闭锁出现异常，异常为{}", e);
        }
        return zookeeperTask.getZookeeperHandler();
    }

}
