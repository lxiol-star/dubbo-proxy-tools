package com.sise.idea.task;

import com.sise.idea.handler.ZookeeperHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author idea
 * @data 2019/4/9
 */
@Slf4j
public class WatchDogTask implements Runnable {

    private Thread zkTaskThread;

    private ZookeeperTask zookeeperTask;

    private ZooKeeper zooKeeper;

    private ZookeeperHandler zookeeperHandler;

    private int connectedTimes = 0;

    private int parkUntilTime = 1000;

    public WatchDogTask(Thread thread, ZookeeperTask zkt, ZooKeeper zk) {
        this.zkTaskThread = thread;
        this.zookeeperTask = zkt;
        this.zooKeeper = zk;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (zookeeperTask.isConnection()) {
                    log.error("------------------！");
                    zookeeperHandler = zookeeperTask.getZookeeperHandler();
                    return;
                } else {
                    if (connectedTimes == 3) {
                        log.error("[链接失败]暂停该线程！");

                        zooKeeper.close();

                        throw new RuntimeException("[zk链接失败]");
                    }
                    log.error("[链接失败]重试！");
                    Thread.sleep(5000);
                    connectedTimes++;
                }
            }
        } catch (InterruptedException e) {
            log.error("[WatchDogTask]看门狗任务执行异常，信息为{}", e);
        }
    }
}
