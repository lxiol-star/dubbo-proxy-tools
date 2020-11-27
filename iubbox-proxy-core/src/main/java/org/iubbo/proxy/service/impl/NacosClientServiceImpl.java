package org.iubbo.proxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.alibaba.nacos.client.naming.NacosNamingService;
import com.alibaba.nacos.client.naming.net.NamingProxy;
import com.alibaba.nacos.common.util.HttpMethod;
import org.iubbo.proxy.model.dto.ServiceInfoResult;
import org.iubbo.proxy.service.NacosClientService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author idea
 * @version V1.0
 * @date 2020/7/2
 */
@Service
public class NacosClientServiceImpl implements NacosClientService {


    @Override
    public List<String> getServiceNameList(String nacosHost, String namespace) throws NacosException, IllegalAccessException {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", nacosHost);
        NamingService namingService = NamingFactory.createNamingService(properties);
        NacosNamingService nacosNamingService = (NacosNamingService) namingService;
        Field[] fields = nacosNamingService.getClass().getDeclaredFields();
        Field serverProxyField = null;
        for (Field field : fields) {
            if (field.getName().equals("serverProxy")) {
                field.setAccessible(true);
                serverProxyField = field;
                break;
            }
        }
        NamingProxy namingProxy = (NamingProxy) serverProxyField.get(namingService);
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("hasIpCount", "true");
        reqParams.put("withInstances", "false");
        reqParams.put("namespaceId", namespace);
        reqParams.put("pageNo", "1");
        reqParams.put("pageSize", "1000");
        String result = namingProxy.reqAPI("/nacos/v1/ns/catalog/services", reqParams, HttpMethod.GET);
        ServiceInfoResult serviceInfoResult = JSON.parseObject(result, ServiceInfoResult.class);
        List<ServiceInfo> serviceInfos = serviceInfoResult.getServiceList();
        List<String> resultList = new ArrayList<>(serviceInfos.size());
        for (ServiceInfo serviceInfo : serviceInfos) {
            String serviceName = serviceInfo.getName();
            serviceName = serviceName.replaceAll("providers:", "").replaceAll("::", "");
            resultList.add(serviceName);
        }
        return resultList;
    }

}
