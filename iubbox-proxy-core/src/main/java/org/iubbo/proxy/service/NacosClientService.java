package org.iubbo.proxy.service;

import com.alibaba.nacos.api.exception.NacosException;

import java.util.List;

/**
 * @author idea
 * @version V1.0
 * @date 2020/7/2
 */
public interface NacosClientService {

    /**
     * 拉取nacos里面的服务列表
     *
     * @param nacosHost
     * @param namespace
     * @return
     */
    List<String> getServiceNameList(String nacosHost, String namespace) throws NacosException, IllegalAccessException;
}
