

package org.iubbo.proxy.service.impl;

import org.I0Itec.zkclient.ZkClient;
import org.iubbo.proxy.service.ZookeeperService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author idea
 * @version V1.0
 * @date 2019/12/24
 */
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

    private String ROOT = "/dubbo";

    private final static Integer TIMEOUT = 3000;

    @Override
    public List<String> getServiceNameList(String zkHost) {
        return this.getServices(zkHost);
    }


    /**
     * 获取zk上边的服务名字信息
     *
     * @return
     */
    private List<String> getServices(String addr) {
        ZkClient zkClient = new ZkClient(addr, TIMEOUT);
        List<String> zkHostList = zkClient.getChildren(ROOT);
        zkClient.close();
        return zkHostList;
    }

    public static void main(String[] args) {
        ZookeeperServiceImpl zk = new ZookeeperServiceImpl();
        List<String> serviceList = zk.getServices("xxx.xxx.xxx.xxx:2181");
        for (String s : serviceList) {
            System.out.println(s);
        }
    }
}
