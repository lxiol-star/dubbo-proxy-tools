package org.iubbo.proxy.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求常量类
 *
 * @author idea
 * @date 2020/1/2
 * @version V1.0
 */
public class CommonConstants {

    public final static String SAMPLE_REQ = "{\n" +
            "    \"interfaceName\": \"com.sise.dubbo.provider.api.EchoService\",\n" +
            "    \"methodName\": \"echoPojo\",\n" +
            "    \"argTypes\": [\n" +
            "        \"com.sise.dubbo.provider.model.Pojo\"\n" +
            "    ],\n" +
            "    \"argObjects\": [\n" +
            "        {\n" +
            "            \"count\": 1,\n" +
            "            \"value\": \"val\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"version\": \"1.0\",\n" +
            "    \"group\": \"test\",\n" +
            "    \"attachments\": {\n" +
            "        \"key\": \"value\"\n" +
            "    }\n" +
            "}";


    @AllArgsConstructor
    @Getter
    public enum  LoadbalanceEnum{

        RANDOM_TYPE(0,"random"),
        ROUND_ROBIN_TYPE(1,"roundrobin"),
        LEASTACTIVE_TYPE(2,"leastactive");

        Integer code;

        String des;

    }


    @Getter
    @AllArgsConstructor
    public enum RegisterTypeEnum{

        ZOOKEEPER(1,"zookeeper"),
        NACOS(2,"nacos");

        Integer code;

        String name;
    }


}
