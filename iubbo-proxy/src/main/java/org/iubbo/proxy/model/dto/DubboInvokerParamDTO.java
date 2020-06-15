package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * invoke 参数封装对象
 *
 * @author idea
 * @date 2019/12/19
 * @version V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DubboInvokerParamDTO {

    private String zkHost;

    /**
     * 直连专用
     */
    private String url;

    /**
     * 服务方法调用超时时间(毫秒)
     */
    private Integer timeout;

    /**
     * random,roundrobin,leastactive 随机，轮询，最少活跃调用
     */
    private String loadbalance;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 每服务消费者每服务每方法最大并发调用数
     */
    private int actives;

    /**
     * 重试次数
     */
    private int retries;

    private String interfaceName;

    private String methodName;

    private String group;

    private String version;

    private String[] argTypes;

    private Object[] argObjects;

    private Map<String, String> attachments;

}
