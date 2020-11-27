package org.iubbo.proxy.service;

import java.util.List;

/**
 * 专门拉取zk信息的方法依赖
 *
 * @author idea
 * @date 2019/12/23
 * @version V1.0
 */
public interface ZookeeperService {

    /**
     * 拉取zk里面的服务列表
     *
     * @param zkHost
     * @return
     */
    List<String> getServiceNameList(String zkHost);
}
