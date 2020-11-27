

package org.iubbo.proxy.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.iubbo.proxy.model.dto.DubboInvokerParamDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 服务引用工厂类
 *
 * @author idea
 * @version V1.0
 * @date 2020/1/2
 */
@Component
public class ReferenceFactory {


    private static String APP_NAME = "dubbo-invoker-proxy";

    private static Integer DEFAULT_TIME_OUT = 3000;

    private static String DEFAULT_LOADBALANCE = CommonConstants.LoadbalanceEnum.RANDOM_TYPE.getDes();

    private static boolean DEFAULT_ASYNC = false;

    private static int DEFAULT_RETRIES = 3;

    /**
     * @return
     */
    public static ReferenceConfig<GenericService> buildReferenceConfig(DubboInvokerParamDTO param) {
        if (StringUtils.isEmpty(param.getRetries())) {
            param.setRetries(DEFAULT_RETRIES);
        }
        if (StringUtils.isEmpty(param.isAsync())) {
            param.setAsync(DEFAULT_ASYNC);
        }
        if (StringUtils.isEmpty(param.getLoadbalance())) {
            param.setLoadbalance(DEFAULT_LOADBALANCE);
        }
        if (StringUtils.isEmpty(param.getTimeout())) {
            param.setTimeout(DEFAULT_TIME_OUT);
        }
        if (StringUtils.isEmpty(param.getRegisterType())){
            //默认使用zk注册中心
            param.setRegisterType("zookeeper");
        }
        boolean isNacosService = param.getRegisterType().contains("nacos");
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(new ApplicationConfig(APP_NAME));

        reference.setInterface(param.getInterfaceName());
        reference.setVersion(param.getVersion());
        reference.setGeneric(true);
        if (!StringUtils.isEmpty(param.getGroup())) {
            reference.setGroup(param.getGroup());
        }
        //默认重试次数为3次
        if (param.getRetries() < 0) {
            reference.setRetries(param.getRetries());
        }
//        reference.setCluster("failfast");
        reference.setTimeout(param.getTimeout());
        reference.setAsync(param.isAsync());
        reference.setActives(param.getActives());
        reference.setLoadbalance(param.getLoadbalance());
        RegistryConfig registry = new RegistryConfig();
        //暂时通过端口来氛
        if (isNacosService) {
            registry.setAddress("nacos://" + param.getServiceAddr());
        } else {
            registry.setAddress("zookeeper://" + param.getServiceAddr());
        }
        if (isNacosService) {
            reference.setGroup(null);
            reference.setActives(0);
            reference.setLoadbalance(null);
            reference.setTimeout(null);
        }
        reference.setRegistry(registry);
        if (!StringUtils.isEmpty(param.getUrl())) {
            reference.setUrl(param.getUrl());
        }
        return reference;
    }
}
