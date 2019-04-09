package com.sise.idea.handler;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.UrlUtils;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.RpcResult;
import com.sise.idea.cache.MethodCache;
import com.sise.idea.cache.UrlCache;
import com.sise.idea.client.NettySimpleClient;
import com.sise.idea.context.ResponseDispatcher;
import com.sise.idea.dto.MethodModelDto;
import com.sise.idea.model.ServiceModel;
import com.sise.idea.util.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.sise.idea.util.ParamUtil.createJsonParamterStr;

/**
 * zookeeper处理器
 *
 * @author idea
 * @data 2019/4/2
 */
@Slf4j
public class ZookeeperHandler implements Watcher {

    private String address;

    private final int timeOut = 5000;

    private int port;

    private String host;

    private ZooKeeper zooKeeper;

    private final static String ROOT = "/dubbo";

    private final static String PROVIDER_END = "/providers";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public ZookeeperHandler(String host, int port) {
        this.host = host;
        this.port = port;
        address = host + ":" + port;
    }

    private ZookeeperHandler() {
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            // 连接时的监听事件
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                countDownLatch.countDown();
                log.info("[zookeeper] 刚刚链接上了zk服务");
                // 子节点变更时的监听
            } else if (Event.EventType.NodeChildrenChanged == event.getType()) {
                try {
                    log.info("[zookeeper] 重新获得Children，并注册监听：" + zooKeeper.getChildren(event.getPath(), true));
                } catch (Exception e) {
                    log.error("[zookeeper] zk出现了异常，异常为{}", e);
                }
            }

        }
    }


    /**
     * 获取链接地址
     */
    public void doConnect() {
        try {
            zooKeeper = new ZooKeeper(address, timeOut, new ZookeeperHandler());
            int i=0;
            while(true){
                if(zooKeeper.getState().isConnected()){
                   break;
                }
                i++;
                Thread.sleep(1000);
                if(i==5){
                    zooKeeper.close();
                    throw new RuntimeException("zk链接异常");
                }
            }
        } catch (IOException e) {
            log.error("[zookeeper] zk链接出现了异常，异常为{}", e);
        } catch (InterruptedException e) {
            log.error("[zookeeper] 闭锁出现中断异常，异常为{}", e);
        }
    }

    /**
     * 获取zk上边的服务信息
     *
     * @return
     */
    public List<ServiceModel> getServices() {
        if (zooKeeper != null) {
            List<ServiceModel> list = new LinkedList<>();
            try {
                List<String> childList = zooKeeper.getChildren(ROOT, null);
                for (String addressName : childList) {
                    list.add(new ServiceModel(addressName));
                }
                return list;
            } catch (Exception e) {
                log.error("[zookeeper] zk链接出现了异常，异常为{}", e);
            }
        }
        return Collections.emptyList();
    }


    public List<URL> getDubboDetail(URL url) {
        ZkClient zkClient = new ZkClient(address, 5000);
        //该服务的函数内容
        List<String> childUrls = zkClient.getChildren(buildServicePath(url.getServiceInterface()));
        List<URL> urls = toUrlsWithoutEmpty(url, childUrls);
        return urls;
    }

    /**
     * zk获取服务详情信息的地址格式
     *
     * @param serviceName
     * @return
     */
    private String buildServicePath(String serviceName) {
        return ROOT + "/" + serviceName + PROVIDER_END;
    }


    /**
     * 将dubbo的url转换为URL集合
     *
     * @param consumer
     * @param providers
     * @return
     */
    private List<URL> toUrlsWithoutEmpty(URL consumer, List<String> providers) {
        List<URL> urls = new ArrayList();
        if (providers != null && !providers.isEmpty()) {
            Iterator iterator = providers.iterator();

            while (iterator.hasNext()) {
                String provider = (String) iterator.next();
                provider = URL.decode(provider);
                if (provider.contains("://")) {
                    URL url = URL.valueOf(provider);
                    if (UrlUtils.isMatch(consumer, url)) {
                        urls.add(url);
                    }
                }
            }
        }

        return urls;
    }

    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2182", 5000, new ZookeeperHandler());
        int i=0;
        while(true){
            Thread.sleep(1000);
            i++;
            if(i==5){
                break;
            }
        }
        zooKeeper.close();
        while(true){
            Thread.sleep(1000);
            i++;
            System.out.println("----");

        }

    }
}


