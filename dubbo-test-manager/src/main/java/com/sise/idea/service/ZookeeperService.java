package com.sise.idea.service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.sise.idea.cache.MethodCache;
import com.sise.idea.cache.UrlCache;
import com.sise.idea.cache.UrlMultiCache;
import com.sise.idea.handler.JarHandler;
import com.sise.idea.handler.ZookeeperHandler;
import com.sise.idea.model.JarPropModel;
import com.sise.idea.model.ServiceModel;
import com.sise.idea.util.ZookeeperHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sise.idea.util.ParamUtil.getHost;
import static com.sise.idea.util.ParamUtil.getPort;

/**
 * @author idea
 * @data 2019/4/8
 */
@Service
@Slf4j
public class ZookeeperService {


    @Autowired
    private JarPropModel jarPropModel;

    private ZookeeperHandlerUtil zookeeperHandlerUtil = new ZookeeperHandlerUtil();

    /**
     * 获取zk里面的服务列表，同时初始化方法缓存
     *
     * @param address
     * @return
     */
    public List<String> getServiceNameList(String address) {
        String host = getHost(address);
        int port = getPort(address);
        ZookeeperHandler zookeeperHandler = new ZookeeperHandler(host, port);
        zookeeperHandler.doConnect();
        List<ServiceModel> serviceModels = zookeeperHandler.getServices();
        List<String> serviceNameList = new ArrayList<>();

        //加载jar里面的方法
        JarHandler jarHandler = new JarHandler();
        //清空缓存
        initCache();

        for (ServiceModel serviceModel : serviceModels) {

            String serviceName = serviceModel.getServiceName();
            Map<String, String> map = new HashMap<>();
            map.put(Constants.INTERFACE_KEY, serviceName);
            URL url = new URL("zookeeper", host, port, map);
            List<URL> urls = zookeeperHandler.getDubboDetail(url);
            for (URL newUrl : urls) {
                //todo 这里面有bug，导致有新的provider注入之后，请求会发生异常
                //URL类缓存
                UrlMultiCache.put(serviceName, newUrl);
            }


            //method 缓存
            jarHandler.loadClassInJar(jarPropModel.getPath(), serviceName);
            serviceNameList.add(serviceName);
        }

        log.info("[InitConfig] 初始化URL类成功！");

        return serviceNameList;
    }


    /**
     * 重新清空缓存
     */
    private void initCache() {
        UrlCache.clean();
        MethodCache.clean();
    }

}
